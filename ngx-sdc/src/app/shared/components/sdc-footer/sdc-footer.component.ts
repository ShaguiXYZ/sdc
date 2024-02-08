import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { SdcSseEventComponent } from '../sdc-sse-event';
import { SwitchThemeComponent } from 'src/app/core/components/header/components';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { SdcKeysComponent } from '../sdc-keys';
import { SdcLogInOutComponent } from '../sdc-log-in-out';

@Component({
  selector: 'sdc-footer',
  styleUrls: ['./sdc-footer.component.scss'],
  template: `
    <footer nxLayout="grid maxwidth nogutters">
      <div class="sdc-footer-content">
        <div class="sdc-footer-info">
          <div class="sdc-log-in-out">
            <sdc-log-in-out />
          </div>
          <span class="sdc-footer-info-text">© 2023</span>
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
  imports: [CommonModule, NxGridModule, SdcKeysComponent, SdcLogInOutComponent, SdcSseEventComponent, SwitchThemeComponent],
  standalone: true
})
export class SdcAppFooterComponent {}
