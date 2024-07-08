import { HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { CacheService, DataInfo, hasValue, HttpService, HttpStatus, TTL } from '@shagui/ng-shagui/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IPageable, ITagDTO, ITagModel } from '../../models/sdc';
import { _TAGS_CACHE_ID_ } from './constants';

@Injectable({ providedIn: 'root' })
export class TagService {
  private _urlTags = `${environment.baseUrl}/api`;

  private readonly cache = inject(CacheService);
  private readonly http = inject(HttpService);

  constructor(private readonly translate: TranslateService) {}

  public tags(page?: number, ps?: number): Promise<IPageable<ITagModel>> {
    let httpParams = new HttpParams();

    if (hasValue(page)) {
      httpParams = httpParams.append('page', String(page));
    }

    if (hasValue(ps)) {
      httpParams = httpParams.append('ps', String(ps));
    }

    return firstValueFrom(
      this.http
        .get<IPageable<ITagDTO>>(`${this._urlTags}/tags`, {
          clientOptions: { params: httpParams },
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: this.translate.instant('Notifications.TagNotFound') }
          },
          cache: { id: _TAGS_CACHE_ID_, ttl: TTL.XS }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<ITagDTO>;
            const result: IPageable<ITagModel> = {
              paging: { ...dto.paging },
              page: dto.page.map(ITagModel.fromDTO)
            };

            return result;
          })
        )
    );
  }

  public componentTags(componentId: number): Promise<IPageable<ITagModel>> {
    return firstValueFrom(
      this.http
        .get<IPageable<ITagDTO>>(`${this._urlTags}/tags/component/${componentId}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: this.translate.instant('Notifications.TagNotFound') }
          }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<ITagDTO>;
            const result: IPageable<ITagModel> = {
              paging: { ...dto.paging },
              page: dto.page.map(ITagModel.fromDTO)
            };

            return result;
          })
        )
    );
  }

  public addTag(componentId: number, name: string, onError?: DataInfo<(error: any) => void>): Promise<ITagModel> {
    return firstValueFrom(
      this.http
        .post<ITagDTO, any>(`${this._urlTags}/tag/create/${componentId}/${name}`, undefined, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: this.translate.instant('Error.404') },
            [HttpStatus.unauthorized]: { text: this.translate.instant('Error.401'), fn: onError?.[HttpStatus.unauthorized] }
          },
          successMessage: { text: this.translate.instant('Notifications.TagAdded') }
        })
        .pipe(map(res => ITagModel.fromDTO(res as ITagDTO)))
    );
  }

  public removeTag(componentId: number, name: string, onError?: DataInfo<(error: any) => void>): Promise<void> {
    return firstValueFrom(
      this.http
        .delete(`${this._urlTags}/tag/delete/${componentId}/${name}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: this.translate.instant('Notifications.TagError') },
            [HttpStatus.unauthorized]: { text: this.translate.instant('Error.401'), fn: onError?.[HttpStatus.unauthorized] }
          },
          successMessage: { text: this.translate.instant('Notifications.TagRemoved') }
        })
        .pipe(map(res => res as void))
    );
  }
  public clearCache = () => this.cache.delete(_TAGS_CACHE_ID_);
}
