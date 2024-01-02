import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { SdcEventBarComponent } from '../sdc-event-bar';
import { SdcGlobalSearchComponent } from '../sdc-global-search';
import { SdcOverlayService } from './services';
import { SdcOverlayModel } from './models';
import $ from 'src/app/core/lib/dom.lib';

@Component({
  selector: 'sdc-overlay',
  styleUrls: ['./sdc-overlay.component.scss'],
  template: `
    <div class="overlay-content">
      <div nxLayout="grid maxwidth nogutters" class="overlay-items">
        <div nxLayout="grid maxwidth nogutters" class="event-bar overlay-item">
          <sdc-event-bar />
        </div>
        <div class="global-search overlay-item" [ngClass]="{ 'item-hidden': !overlayModel.showGlobalSearch }">
          <sdc-global-search />
        </div>
      </div>
    </div>
  `,
  standalone: true,
  imports: [CommonModule, NxGridModule, SdcEventBarComponent, SdcGlobalSearchComponent]
})
export class SdcOverlayComponent {
  public overlayModel: SdcOverlayModel = { showGlobalSearch: false };

  constructor(overlayService: SdcOverlayService) {
    overlayService.onDataChange().subscribe(data => {
      const { showGlobalSearch } = this.overlayModel;

      this.overlayModel = { ...this.overlayModel, ...data };

      if (!showGlobalSearch && this.overlayModel.showGlobalSearch) {
        setTimeout(() => $('.sdc-search-input input')?.focus(), 300);
      }
    });
  }
}
