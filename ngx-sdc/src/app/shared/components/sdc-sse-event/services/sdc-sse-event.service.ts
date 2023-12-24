import { Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject, Subscription } from 'rxjs';
import { ContextDataService, SseEventModel, SseService } from 'src/app/core/services';
import { ContextDataInfo } from 'src/app/shared/constants';
import { SdcEventReference, SdcRootContextData } from 'src/app/shared/models';

@Injectable()
export class SdcSseEventService implements OnDestroy {
  private subscriptions$: Subscription[] = [];
  private data$: Subject<SseEventModel<SdcEventReference>[]>;

  constructor(
    private readonly contextDataService: ContextDataService,
    private readonly sseService: SseService<SdcEventReference>
  ) {
    this.data$ = new Subject();
    this.subscriptions$.push(this.eventObserver(), this.contextDataObserver());
  }

  ngOnDestroy(): void {
    this.subscriptions$.forEach(subscription => subscription.unsubscribe());
  }

  public loadData(): void {
    const contextData = this.contextDataService.get<SdcRootContextData>(ContextDataInfo.ROOT_DATA);
    const { events, eventsState } = contextData || { events: [], eventsState: 'closed' };

    this.contextDataService.set<SdcRootContextData>(
      ContextDataInfo.ROOT_DATA,
      { events, eventsState },
      { persistent: true, referenced: true }
    );

    this.data$.next(events);
  }

  public toggleEvents(): void {
    const contextData = this.contextDataService.get<SdcRootContextData>(ContextDataInfo.ROOT_DATA);
    const eventsState = contextData.eventsState === 'open' ? 'closed' : 'open';

    this.contextDataService.set<SdcRootContextData>(ContextDataInfo.ROOT_DATA, { ...contextData, eventsState });
  }

  public onDataChange(): Observable<SseEventModel[]> {
    return this.data$.asObservable();
  }

  private eventObserver = (): Subscription =>
    this.sseService.onEvent().subscribe(event => {
      const contextData = this.contextDataService.get<SdcRootContextData>(ContextDataInfo.ROOT_DATA);
      contextData.events = [...contextData.events, event];
      this.contextDataService.set(ContextDataInfo.ROOT_DATA, contextData);

      this.data$.next(contextData.events);
    });

  private contextDataObserver = (): Subscription =>
    this.contextDataService.onDataChange<SdcRootContextData>(ContextDataInfo.ROOT_DATA).subscribe(contextData => {
      this.data$.next(contextData.events);
    });
}
