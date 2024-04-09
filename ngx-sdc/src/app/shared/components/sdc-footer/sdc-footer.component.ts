import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { ContextDataService } from '@shagui/ng-shagui/core';
import { SwitchThemeComponent } from 'src/app/core/components/header/components';
import { IAppConfigurationModel } from 'src/app/core/models/sdc';
import { ContextDataInfo } from '../../constants';
import { SdcKeysComponent } from '../sdc-keys';
import { SdcLogInOutComponent } from '../sdc-log-in-out';
import { SdcSseEventComponent } from '../sdc-sse-event';

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
          @if (appConfig && appConfig.copyright) {
            <span class="sdc-footer-info-text">© {{ appConfig.copyright }}</span>
          }
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
export class SdcAppFooterComponent {
  constructor(private readonly contextDataService: ContextDataService) {}

  public get appConfig(): IAppConfigurationModel | undefined {
    return this.contextDataService.get<IAppConfigurationModel>(ContextDataInfo.APP_CONFIG);
  }
}
