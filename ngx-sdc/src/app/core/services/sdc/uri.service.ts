import { inject, Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { HttpService, HttpStatus, TTL } from '@shagui/ng-shagui/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IUriDTO, IUriModel, UriType } from '../../models/sdc';
import { _URI_CACHE_ID_ } from './constants';

@Injectable({ providedIn: 'root' })
export class UriService {
  private _urlUris = `${environment.baseUrl}/api`;

  private readonly http = inject(HttpService);

  constructor(private readonly translate: TranslateService) {}

  public componentUriByType(componentId: number, type: UriType): Promise<IUriModel> {
    return firstValueFrom(
      this.http
        .get<IUriDTO>(`${this._urlUris}/uri/component/${componentId}/type/${type}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: this.translate.instant('Notifications.UriNotFound') }
          },
          cache: { id: this.uriCacheId(componentId, type), ttl: TTL.XS }
        })
        .pipe(map(res => IUriModel.fromDTO(res as IUriDTO)))
    );
  }

  private uriCacheId = (componentId: number, type: UriType): string => `${_URI_CACHE_ID_}_URI_${componentId}_${type}_`;
}
