import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { SdcSseEventComponent } from '../sdc-sse-event';
import { SwitchThemeComponent } from 'src/app/core/components/header/components';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { SdcKeysComponent } from '../sdc-keys';

@Component({
  selector: 'sdc-footer',
  styles: [
    `
      footer {
        bottom: 0;
        display: flex;
        max-height: 50px;
        overflow: hidden;
        position: absolute;
        text-align: center;
        width: 100%;

        .sdc-footer-info {
          display: flex;
          align-items: center;
          flex: 1;
          justify-content: flex-start;
        }

        .sdc-footer-content {
          align-items: center;
          border-top: 1px solid var(--header-border-color);
          display: flex;
          flex-direction: row;
          width: 100%;

          .sdc-footer-actions {
            border-left: 1px solid var(--header-border-color);

            .sdc-sse-event {
              height: 15px;
              margin: 12px;
              width: 15px;
            }
          }
        }
      }
    `
  ],
  template: `
    <footer nxLayout="grid maxwidth nogutters">
      <div class="sdc-footer-content">
        <div class="sdc-footer-info">
          <sdc-keys />
        </div>
        <div class="sdc-footer-actions sdc-center">
          <nx-switch-theme />
          <div class="sdc-sse-event">
            <sdc-sse-event />
          </div>
        </div>
      </div>
    </footer>
  `,
  imports: [CommonModule, NxGridModule, SdcKeysComponent, SdcSseEventComponent, SwitchThemeComponent],
  standalone: true
})
export class SdcAppFooterComponent {}
