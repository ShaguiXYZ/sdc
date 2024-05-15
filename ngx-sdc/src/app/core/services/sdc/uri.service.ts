import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { HttpService, HttpStatus } from '@shagui/ng-shagui/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IUriDTO, IUriModel, UriType } from '../../models/sdc';
import { XS_EXPIRATON_TIME, _URI_CACHE_ID_ } from './constants';

@Injectable({ providedIn: 'root' })
export class UriService {
  private _urlUris = `${environment.baseUrl}/api`;

  constructor(
    private readonly http: HttpService,
    private readonly translate: TranslateService
  ) {}

  public componentUriByType(componentId: number, type: UriType): Promise<IUriModel> {
    return firstValueFrom(
      this.http
        .get<IUriDTO>(`${this._urlUris}/uri/component/${componentId}/type/${type}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: this.translate.instant('Notifications.UriNotFound') }
          },
          cache: { id: this.uriCacheId(componentId), ttl: XS_EXPIRATON_TIME }
        })
        .pipe(map(res => IUriModel.fromDTO(res as IUriDTO)))
    );
  }

  private uriCacheId = (componentId: number): string => `${_URI_CACHE_ID_}_URI_${componentId}_`;
}