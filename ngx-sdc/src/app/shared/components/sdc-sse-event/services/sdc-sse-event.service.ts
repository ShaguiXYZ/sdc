import { Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject, Subscription } from 'rxjs';
import { ContextDataService, SseEventModel, SseService } from 'src/app/core/services';
import { ContextDataInfo } from 'src/app/shared/constants';
import { SdcEventReference, SdcRootContextData } from 'src/app/shared/models';

@Injectable()
export class SdcSseEventService implements OnDestroy {
  private closeTimeout?: any;
  private subscriptions$: Subscription[] = [];
  private data$: Subject<SseEventModel<SdcEventReference>[]> = new Subject();

  constructor(
    private readonly contextDataService: ContextDataService,
    private readonly sseService: SseService<SdcEventReference>
  ) {
    this.subscriptions$.push(this.eventObserver(), this.contextDataObserver());
  }

  ngOnDestroy(): void {
    this.subscriptions$.forEach(subscription => subscription.unsubscribe());
    this.sseService.close();
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

  public onDataChange(): Observable<SseEventModel<SdcEventReference>[]> {
    return this.data$.asObservable();
  }

  private eventObserver = (): Subscription =>
    this.sseService.onEvent().subscribe(event => {
      if (this.closeTimeout) {
        clearTimeout(this.closeTimeout);
        this.closeTimeout = undefined;
      }

      const contextData = this.contextDataService.get<SdcRootContextData>(ContextDataInfo.ROOT_DATA);

      contextData.events = [...contextData.events, event];
      this.contextDataService.set(ContextDataInfo.ROOT_DATA, contextData);

      this.data$.next(contextData.events);
    });

  private contextDataObserver = (): Subscription =>
    this.contextDataService.onDataChange<SdcRootContextData>(ContextDataInfo.ROOT_DATA).subscribe(contextData => {
      this.data$.next(contextData.events);
    });

  public toggleEvents(): void {
    const contextData = this.contextDataService.get<SdcRootContextData>(ContextDataInfo.ROOT_DATA);
    const eventsState = contextData.eventsState === 'open' ? 'closed' : 'open';

    if (eventsState === 'open' && contextData.events.length === 0) {
      this.closeTimeout = setTimeout(() => this.toggleEvents(), 5000);
    } else if (this.closeTimeout) {
      clearTimeout(this.closeTimeout);
      this.closeTimeout = undefined;
    }

    this.contextDataService.set<SdcRootContextData>(ContextDataInfo.ROOT_DATA, { ...contextData, eventsState });
  }
}
