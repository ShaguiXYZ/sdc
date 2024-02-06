import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NxGridModule } from '@aposin/ng-aquila/grid';
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
          <sdc-event-bar [state]="overlayModel.eventBarState" />
        </div>
        <div class="global-search overlay-item">
          <sdc-global-search [state]="overlayModel.globalSearchState" />
        </div>
        <div class="help overlay-item">
          <sdc-help [state]="overlayModel.helpState" help="squads" />
        </div>
        <div class="login overlay-item">
          <sdc-login [state]="overlayModel.loginState" />
        </div>
      </div>
    }
  `,
  standalone: true,
  imports: [CommonModule, NxGridModule, SdcEventBarComponent, SdcGlobalSearchComponent, SdcHelpComponent, SdcLoginComponent]
})
export class SdcOverlayComponent {
  public overlayModel: SdcOverlayModel = SdcOverlayService.DEFAULT_OVERLAY_STATE;

  constructor(overlayService: SdcOverlayService) {
    overlayService.onDataChange().subscribe(data => {
      this.overlayModel = { ...this.overlayModel, ...data };
    });
  }
}
