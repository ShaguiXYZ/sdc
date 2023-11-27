import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { hasValue } from '../../lib';
import { IPageable, ITagDTO, ITagModel } from '../../models/sdc';
import { CacheService } from '../context-data';
import { HttpService, HttpStatus } from '../http';

@Injectable({ providedIn: 'root' })
export class TagService {
  private _urlTags = `${environment.baseUrl}/api`;

  constructor(private cache: CacheService, private http: HttpService) {}

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
            [HttpStatus.notFound]: { text: 'Notifications.TagNotFound' }
          }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<ITagDTO>;
            const result: IPageable<ITagModel> = {
              paging: { ...dto.paging },
              page: dto.page.map(ITagModel.toModel)
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
            [HttpStatus.notFound]: { text: 'Notifications.TagNotFound' }
          }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<ITagDTO>;
            const result: IPageable<ITagModel> = {
              paging: { ...dto.paging },
              page: dto.page.map(ITagModel.toModel)
            };

            return result;
          })
        )
    );
  }

  public addTag(componentId: number, name: string): Promise<ITagModel> {
    return firstValueFrom(
      this.http
        .post<ITagDTO, any>(`${this._urlTags}/tag/create/${componentId}/${name}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Notifications.TagError' }
          }
        })
        .pipe(map(res => ITagModel.toModel(res as ITagDTO)))
    );
  }

  public removeTag(componentId: number, name: string): Promise<void> {
    return firstValueFrom(
      this.http
        .delete(`${this._urlTags}/tag/delete/${componentId}/${name}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Notifications.TagError' }
          }
        })
        .pipe(map(res => res as void))
    );
  }
}
