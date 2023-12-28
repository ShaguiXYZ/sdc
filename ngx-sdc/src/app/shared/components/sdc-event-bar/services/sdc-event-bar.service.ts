import { Injectable, OnDestroy } from '@angular/core';
import { Subject, Subscription } from 'rxjs';
import { ContextDataService, SseEventModel } from 'src/app/core/services';
import { ContextDataInfo } from 'src/app/shared/constants';
import { SdcRootContextData } from 'src/app/shared/models';
import { SdcEventBarData } from '../models';

@Injectable()
export class SdcEventBarService implements OnDestroy {
  private subscriptions$: Subscription[] = [];
  private data$: Subject<Partial<SdcEventBarData>> = new Subject();

  constructor(private readonly contextDataService: ContextDataService) {
    this.subscriptions$.push(
      this.contextDataService.onDataChange<SdcRootContextData>(ContextDataInfo.ROOT_DATA).subscribe(contextData => {
        this.data$.next({ events: contextData.events.filter(event => event.type === 'ERROR'), state: contextData.eventsState });
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions$.forEach(subscription => subscription.unsubscribe());
  }

  public onDataChange(): Subject<Partial<SdcEventBarData>> {
    return this.data$;
  }

  public removeEvent(event: SseEventModel): void {
    const contextData = this.contextDataService.get<SdcRootContextData>(ContextDataInfo.ROOT_DATA);
    const events = contextData.events.filter(e => e.id !== event.id);

    this.contextDataService.set<SdcRootContextData>(ContextDataInfo.ROOT_DATA, {
      ...contextData,
      events,
      eventsState: events.length ? contextData.eventsState : 'closed'
    });
  }

  public readEvent(event: SseEventModel): void {
    const contextData = this.contextDataService.get<SdcRootContextData>(ContextDataInfo.ROOT_DATA);
    const events = contextData.events.map(e => (e.id === event.id ? { ...e, read: !e.read } : e));

    this.contextDataService.set<SdcRootContextData>(ContextDataInfo.ROOT_DATA, { ...contextData, events });
  }

  public toggleEvents(): void {
    const contextData = this.contextDataService.get<SdcRootContextData>(ContextDataInfo.ROOT_DATA);
    const eventsState = contextData.eventsState === 'open' ? 'closed' : 'open';

    this.contextDataService.set<SdcRootContextData>(ContextDataInfo.ROOT_DATA, { ...contextData, eventsState });
  }

  public markAllAsRead(): void {
    const contextData = this.contextDataService.get<SdcRootContextData>(ContextDataInfo.ROOT_DATA);
    const events = contextData.events.map(e => ({ ...e, read: true }));

    this.contextDataService.set<SdcRootContextData>(ContextDataInfo.ROOT_DATA, { ...contextData, events });
  }

  public clearEvents = (): void => {
    const contextData = this.contextDataService.get<SdcRootContextData>(ContextDataInfo.ROOT_DATA);

    this.contextDataService.set<SdcRootContextData>(ContextDataInfo.ROOT_DATA, { ...contextData, events: [], eventsState: 'closed' });
  };
}
