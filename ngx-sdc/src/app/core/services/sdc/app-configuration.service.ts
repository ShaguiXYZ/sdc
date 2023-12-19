import { Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IAppConfiguration } from '../../models/sdc';
import { HttpService, HttpStatus } from '../http';
import { _CONFIGURATION_CACHE_ID_ } from './constants';

@Injectable({ providedIn: 'root' })
export class AppConfigurationService {
  private _urlConfiguration = `${environment.baseUrl}/api/configurations`;

  constructor(private readonly http: HttpService) {}

  public AppConfiguracions(): Promise<IAppConfiguration> {
    return firstValueFrom(
      this.http
        .get<IAppConfiguration>(`${this._urlConfiguration}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Notifications.ConfigurationNotFound' }
          },
          cache: this.configurationCacheId()
        })
        .pipe(map(res => res as IAppConfiguration))
    );
  }

  private configurationCacheId(): string {
    return _CONFIGURATION_CACHE_ID_;
  }
}
