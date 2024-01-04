import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { OverlayItemState, SdcOverlayModel } from '../models';

@Injectable({
  providedIn: 'root'
})
export class SdcOverlayService {
  private overlayModel: SdcOverlayModel = { globalSearchState: 'closed', eventBarState: 'closed' };
  private data$: Subject<SdcOverlayModel> = new Subject<SdcOverlayModel>();

  public onDataChange() {
    return this.data$.asObservable();
  }

  public toggleGlobalSearch(state?: OverlayItemState) {
    this.overlayModel.globalSearchState = state ?? OverlayItemState.toggle(this.overlayModel.globalSearchState);
    this.data$.next(this.overlayModel);
  }

  public toggleEventBar(state?: OverlayItemState) {
    this.overlayModel.eventBarState = state ?? OverlayItemState.toggle(this.overlayModel.eventBarState);
    this.data$.next(this.overlayModel);
  }

  public defaultOverlayState() {
    this.overlayModel = { ...this.overlayModel, globalSearchState: 'closed' };
    this.data$.next(this.overlayModel);
  }
}
