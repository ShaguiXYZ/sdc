import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { SdcSseEventComponent } from '../sdc-sse-event';
import { SwitchThemeComponent } from 'src/app/core/components/header/components';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { SdcKeysComponent } from '../sdc-keys';
import { SdcLogInOutComponent } from '../sdc-log-in-out';
import { ContextDataService } from 'src/app/core/services';
import { IAppConfigurationModel } from 'src/app/core/models/sdc';
import { ContextDataInfo } from '../../constants';

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
          @if (appConfig.copyright) {
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
export class SdcAppFooterComponent implements OnInit {
  public appConfig!: IAppConfigurationModel;

  constructor(private readonly contextDataService: ContextDataService) {}

  ngOnInit(): void {
    this.appConfig = this.contextDataService.get<IAppConfigurationModel>(ContextDataInfo.APP_CONFIG);
  }
}
