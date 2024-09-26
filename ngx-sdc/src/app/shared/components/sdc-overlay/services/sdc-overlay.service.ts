import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { OverlayItemStatus, SdcOverlayModel } from '../models';

@Injectable({
  providedIn: 'root'
})
export class SdcOverlayService {
  public static readonly DEFAULT_OVERLAY_STATE: SdcOverlayModel = {
    eventBarState: { status: 'closed' },
    globalSearchState: { status: 'closed' },
    helpState: { status: 'closed' },
    loginState: { status: 'closed' }
  };

  private overlayModel: SdcOverlayModel = SdcOverlayService.DEFAULT_OVERLAY_STATE;
  private data$: BehaviorSubject<SdcOverlayModel> = new BehaviorSubject<SdcOverlayModel>(this.overlayModel);

  public onDataChange(): Observable<SdcOverlayModel> {
    return this.data$.asObservable();
  }

  public toggleEventBar(status?: OverlayItemStatus) {
    this.overlayModel.eventBarState.status = status ?? OverlayItemStatus.toggle(this.overlayModel.eventBarState.status);
    this.data$.next(this.overlayModel);
  }

  public toggleGlobalSearch(status?: OverlayItemStatus) {
    this.defaultOverlayState();
    this.overlayModel.globalSearchState.status = status ?? OverlayItemStatus.toggle(this.overlayModel.globalSearchState.status);
    this.data$.next(this.overlayModel);
  }

  public toggleHelp(status?: OverlayItemStatus) {
    this.defaultOverlayState();
    this.overlayModel.helpState.status = status ?? OverlayItemStatus.toggle(this.overlayModel.helpState.status);
    this.data$.next(this.overlayModel);
  }

  public toggleLogin() {
    if (this.overlayModel.loginState.loaded) {
      this.overlayModel.loginState.status = 'closed';

      const timeout = setTimeout(() => {
        this.overlayModel.loginState.loaded = false;
        clearTimeout(timeout);
      }, 200);
    } else {
      this.overlayModel.loginState.loaded = true;

      const timeout = setTimeout(() => {
        this.overlayModel.loginState.status = 'open';
        clearTimeout(timeout);
      }, 50);
    }

    this.data$.next(this.overlayModel);
  }

  public defaultOverlayState() {
    this.overlayModel = {
      ...this.overlayModel,
      globalSearchState: { status: 'closed' },
      helpState: { status: 'closed' },
      loginState: { status: 'closed' }
    };

    this.data$.next(this.overlayModel);
  }
}
