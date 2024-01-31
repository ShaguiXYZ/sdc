import { Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IAppConfigurationDTO, IAppConfigurationModel } from '../../models/sdc';
import { HttpService, HttpStatus } from '../http';
import { _CONFIGURATION_CACHE_ID_ } from './constants';

@Injectable({ providedIn: 'root' })
export class AppConfigurationService {
  private _urlConfiguration = `${environment.baseUrl}/api/configurations`;

  constructor(private readonly http: HttpService) {}

  public appConfiguracions(): Promise<IAppConfigurationModel> {
    return firstValueFrom(
      this.http
        .get<IAppConfigurationDTO>(`${this._urlConfiguration}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Notifications.ConfigurationNotFound' }
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
