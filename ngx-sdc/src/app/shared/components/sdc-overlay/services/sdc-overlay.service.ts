import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { SdcOverlayModel } from '../models';

@Injectable({
  providedIn: 'root'
})
export class SdcOverlayService {
  private overlayModel: SdcOverlayModel = { showGlobalSearch: false };
  private data$: Subject<SdcOverlayModel> = new Subject<SdcOverlayModel>();

  public onDataChange() {
    return this.data$.asObservable();
  }

  public toggleGlobalSearch(state?: boolean) {
    this.overlayModel.showGlobalSearch = state ?? !this.overlayModel.showGlobalSearch;
    this.data$.next(this.overlayModel);
  }

  public defaultOverlayState() {
    this.toggleGlobalSearch(false);
  }
}
