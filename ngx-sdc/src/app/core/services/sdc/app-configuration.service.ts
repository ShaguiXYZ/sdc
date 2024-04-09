import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { HttpService, HttpStatus } from '@shagui/ng-shagui/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IAppConfigurationDTO, IAppConfigurationModel } from '../../models/sdc';
import { _CONFIGURATION_CACHE_ID_ } from './constants';

@Injectable({ providedIn: 'root' })
export class AppConfigurationService {
  private _urlConfiguration = `${environment.baseUrl}/api/configurations`;

  constructor(
    private readonly http: HttpService,
    private readonly translate: TranslateService
  ) {}

  public appConfiguracions(): Promise<IAppConfigurationModel> {
    return firstValueFrom(
      this.http
        .get<IAppConfigurationDTO>(`${this._urlConfiguration}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: this.translate.instant('Notifications.ConfigurationNotFound') }
          },
          cache: this.configurationCacheId()
        })
        .pipe(map(res => IAppConfigurationModel.fromDTO(res as IAppConfigurationDTO)))
    );
  }

  private configurationCacheId(): string {
    return _CONFIGURATION_CACHE_ID_;
  }
}
