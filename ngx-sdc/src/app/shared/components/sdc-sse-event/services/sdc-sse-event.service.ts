import { Injectable, OnDestroy } from '@angular/core';
import { ContextDataService } from '@shagui/ng-shagui/core';
import { Observable, Subject, Subscription } from 'rxjs';
import { SseEventModel, SseService } from 'src/app/core/services';
import { ContextDataInfo } from 'src/app/shared/constants';
import { SdcOverlayContextData } from 'src/app/shared/models';
import { SdcOverlayService } from '../../sdc-overlay/services';

@Injectable()
export class SdcSseEventService implements OnDestroy {
  private subscriptions$: Subscription[] = [];
  private data$: Subject<SseEventModel[]> = new Subject();

  constructor(
    private readonly contextDataService: ContextDataService,
    private readonly overlayService: SdcOverlayService,
    private readonly sseService: SseService
  ) {
    this.subscriptions$.push(this.eventObserver(), this.contextDataObserver());
  }

  ngOnDestroy(): void {
    this.subscriptions$.forEach(subscription => subscription.unsubscribe());
    this.sseService.close();
  }

  public loadData(): void {
    const contextData = this.contextDataService.get<SdcOverlayContextData>(ContextDataInfo.OVERLAY_DATA);
    const { events } = contextData || { events: [] };

    this.contextDataService.set<SdcOverlayContextData>(ContextDataInfo.OVERLAY_DATA, { events }, { persistent: true, referenced: true });

    this.data$.next(events);
  }

  public onDataChange(): Observable<SseEventModel[]> {
    return this.data$.asObservable();
  }

  public toggleEvents(): void {
    this.overlayService.toggleEventBar();
  }

  private eventObserver = (): Subscription =>
    this.sseService.onEvent().subscribe(event => {
      const overlayContextData = this.contextDataService.get<SdcOverlayContextData>(ContextDataInfo.OVERLAY_DATA);

      overlayContextData.events = [...overlayContextData.events, event];
      this.contextDataService.set(ContextDataInfo.OVERLAY_DATA, overlayContextData);

      this.data$.next(overlayContextData.events);
    });

  private contextDataObserver = (): Subscription =>
    this.contextDataService.onDataChange<SdcOverlayContextData>(ContextDataInfo.OVERLAY_DATA).subscribe(contextData => {
      this.data$.next(contextData.events);
    });
}
