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

        .event-bar {
          display: flex;
          justify-content: flex-end;
          height: 100%;
          padding-bottom: 45px;
          padding-top: 55px;
          z-index: $z-index-middle;
        }
      }
    `
  ],
  template: `
    <div class="overlay-content">
      <div nxLayout="grid maxwidth nogutters" class="event-bar">
        <sdc-event-bar />
      </div>
    </div>
  `,
  standalone: true,
  imports: [CommonModule, NxGridModule, SdcEventBarComponent]
})
export class SdcAppOverlayComponent {}
