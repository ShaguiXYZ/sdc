import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { SdcEventBarComponent } from '../sdc-event-bar';

@Component({
  selector: 'sdc-overlay',
  styles: [
    `
      @import 'core-globals';

      .overlay-content {
        height: 100vh;
        pointer-events: none;
        position: absolute;
        width: 100vw;
        z-index: $z-index-middle;

        .overlay-items {
          height: 100%;
          pointer-events: none;
          position: relative;

          .overlay-item {
            position: absolute;
          }

          .event-bar {
            display: flex;
            justify-content: flex-end;
            height: 100%;
            padding-bottom: 45px;
            padding-top: 55px;
            right: 0;
            top: 0;
          }
        }
      }
    `
  ],
  template: `
    <div class="overlay-content">
      <div nxLayout="grid maxwidth nogutters" class="overlay-items">
        <div nxLayout="grid maxwidth nogutters" class="event-bar overlay-item">
          <sdc-event-bar />
        </div>
      </div>
    </div>
  `,
  standalone: true,
  imports: [CommonModule, NxGridModule, SdcEventBarComponent]
})
export class SdcOverlayComponent {}
