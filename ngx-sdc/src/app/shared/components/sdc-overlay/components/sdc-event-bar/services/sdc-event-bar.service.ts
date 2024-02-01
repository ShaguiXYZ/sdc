import { Injectable, OnDestroy } from '@angular/core';
import { Subject, Subscription } from 'rxjs';
import { ContextDataService, SseEventModel } from 'src/app/core/services';
import { ContextDataInfo } from 'src/app/shared/constants';
import { SdcRootContextData } from 'src/app/shared/models';
import { SdcOverlayService } from '../../../services';
import { SdcEventBarData } from '../models';

@Injectable()
export class SdcEventBarService implements OnDestroy {
  private subscriptions$: Subscription[] = [];
  private data$: Subject<Partial<SdcEventBarData>> = new Subject();

  constructor(
    private readonly contextDataService: ContextDataService,
    private readonly overlayService: SdcOverlayService
  ) {
    this.subscriptions$.push(
      this.contextDataService.onDataChange<SdcRootContextData>(ContextDataInfo.OVERLAY_DATA).subscribe(contextData => {
        this.data$.next({ events: contextData.events.filter(event => event.type === 'ERROR') });
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions$.forEach(subscription => subscription.unsubscribe());
  }

  public initialize(): void {
    const contextData = this.contextDataService.get<SdcRootContextData>(ContextDataInfo.OVERLAY_DATA);

    this.data$.next({ events: contextData.events.filter(event => event.type === 'ERROR') });
  }

  public onDataChange(): Subject<Partial<SdcEventBarData>> {
    return this.data$;
  }

  public removeEvent(event: SseEventModel): void {
    const contextData = this.contextDataService.get<SdcRootContextData>(ContextDataInfo.OVERLAY_DATA);
    const events = contextData.events.filter(e => e.id !== event.id);

    this.contextDataService.set<SdcRootContextData>(ContextDataInfo.OVERLAY_DATA, {
      ...contextData,
      events
    });
  }

  public readEvent(event: SseEventModel): void {
    const contextData = this.contextDataService.get<SdcRootContextData>(ContextDataInfo.OVERLAY_DATA);
    const events = contextData.events.map(e => (e.id === event.id ? { ...e, read: !e.read } : e));

    this.contextDataService.set<SdcRootContextData>(ContextDataInfo.OVERLAY_DATA, { ...contextData, events });
  }

  public markAllAsRead(): void {
    const contextData = this.contextDataService.get<SdcRootContextData>(ContextDataInfo.OVERLAY_DATA);
    const events = contextData.events.map(e => ({ ...e, read: true }));

    this.contextDataService.set<SdcRootContextData>(ContextDataInfo.OVERLAY_DATA, { ...contextData, events });
  }

  public clearEvents = (): void => {
    const contextData = this.contextDataService.get<SdcRootContextData>(ContextDataInfo.OVERLAY_DATA);

    this.contextDataService.set<SdcRootContextData>(ContextDataInfo.OVERLAY_DATA, { ...contextData, events: [] });
  };

  public toggleEvents(): void {
    this.overlayService.toggleEventBar();
  }
}
