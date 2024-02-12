import { CommonModule } from '@angular/common';
import { Component, OnDestroy } from '@angular/core';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { Subscription } from 'rxjs';
import { SdcEventBarComponent, SdcGlobalSearchComponent, SdcHelpComponent, SdcLoginComponent } from './components';
import { SdcOverlayModel } from './models';
import { SdcOverlayService } from './services';

@Component({
  selector: 'sdc-overlay',
  styleUrls: ['./sdc-overlay.component.scss'],
  template: `
    @defer {
      <div nxLayout="grid maxwidth nogutters" class="overlay-items">
        <div nxLayout="grid maxwidth nogutters" class="event-bar overlay-item">
          <sdc-event-bar [state]="overlayModel.eventBarState.status" />
        </div>
        <div class="global-search overlay-item">
          <sdc-global-search [state]="overlayModel.globalSearchState.status" />
        </div>
        <div class="help overlay-item">
          <sdc-help [state]="overlayModel.helpState.status" help="squads" />
        </div>
        @if (overlayModel.loginState.loaded) {
          <div class="login overlay-item">
            <sdc-login [state]="overlayModel.loginState.status" />
          </div>
        }
      </div>
    }
  `,
  standalone: true,
  imports: [CommonModule, NxGridModule, SdcEventBarComponent, SdcGlobalSearchComponent, SdcHelpComponent, SdcLoginComponent]
})
export class SdcOverlayComponent implements OnDestroy {
  public overlayModel: SdcOverlayModel = SdcOverlayService.DEFAULT_OVERLAY_STATE;

  private subcriptions: Subscription[] = [];

  constructor(overlayService: SdcOverlayService) {
    this.subcriptions.push(
      overlayService.onDataChange().subscribe(data => {
        this.overlayModel = { ...this.overlayModel, ...data };
      })
    );
  }

  ngOnDestroy(): void {
    this.subcriptions.forEach(subscription => subscription.unsubscribe());
  }
}
