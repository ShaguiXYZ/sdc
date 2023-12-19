import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom, map, tap } from 'rxjs';
import { METRIC_HISTORY_ELEMENTS } from 'src/app/shared/constants';
import { environment } from 'src/environments/environment';
import { hasValue } from '../../lib';
import { IComponentDTO, IComponentModel, ICoverageModel, IMetricDTO, IMetricModel, IPageable } from '../../models/sdc';
import { IHistoricalCoverage } from '../../models/sdc/historical-coverage.model';
import { CacheService } from '../context-data';
import { HttpService, HttpStatus } from '../http';
import { XS_EXPIRATON_TIME, _COMPONENT_CACHE_ID_ } from './constants';

@Injectable({ providedIn: 'root' })
export class ComponentService {
  private _urlComponents = `${environment.baseUrl}/api`;

  constructor(
    private readonly cache: CacheService,
    private readonly http: HttpService
  ) {}

  public component(componentId: number): Promise<IComponentModel> {
    return firstValueFrom(
      this.http
        .get<IComponentDTO>(`${this._urlComponents}/component/${componentId}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Notifications.ComponentNotFound' }
          }
        })
        .pipe(map(res => IComponentModel.toModel(res as IComponentDTO)))
    );
  }

  public squadComponents(squadId: number): Promise<IPageable<IComponentModel>> {
    return firstValueFrom(
      this.http
        .get<IPageable<IComponentDTO>>(`${this._urlComponents}/components/squad/${squadId}`, {
          showLoading: true,
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Notifications.ComponentsNotFound' }
          },
          cache: { id: this.squadCacheId(squadId), ttl: XS_EXPIRATON_TIME }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<IComponentDTO>;
            const result: IPageable<IComponentModel> = {
              paging: { ...dto.paging },
              page: dto.page.filter(data => hasValue(data.coverage)).map(IComponentModel.toModel)
            };

            return result;
          })
        )
    );
  }

  public filter(
    name?: string,
    squadId?: number,
    tags?: string[],
    coverageMin?: number,
    coverageMax?: number,
    page?: number,
    ps?: number,
    showLoading: boolean = true
  ): Promise<IPageable<IComponentModel>> {
    let httpParams = new HttpParams();

    if (hasValue(page)) {
      httpParams = httpParams.append('page', String(page));
    }

    if (hasValue(ps)) {
      httpParams = httpParams.append('ps', String(ps));
    }

    if (name) {
      httpParams = httpParams.append('name', name);
    }

    if (hasValue(squadId)) {
      httpParams = httpParams.append('squadId', String(squadId));
    }

    if (tags?.length) {
      httpParams = httpParams.append('tags', tags.join(','));
    }

    if (hasValue(coverageMin)) {
      httpParams = httpParams.append('coverageMin', String(coverageMin));
    }

    if (coverageMax) {
      httpParams = httpParams.append('coverageMax', String(coverageMax));
    }

    return firstValueFrom(
      this.http
        .get<IPageable<IComponentDTO>>(`${this._urlComponents}/components/filter`, {
          showLoading,
          clientOptions: { params: httpParams },
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Notifications.ComponentsNotFound' }
          }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<IComponentDTO>;
            const result: IPageable<IComponentModel> = {
              paging: { ...dto.paging },
              page: dto.page.map(IComponentModel.toModel)
            };

            return result;
          })
        )
    );
  }

  public componentMetrics(componentId: number): Promise<IPageable<IMetricModel>> {
    return firstValueFrom(
      this.http
        .get<IPageable<IMetricDTO>>(`${this._urlComponents}/component/${componentId}/metrics`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Notifications.MetricsNotFound' }
          }
        })
        .pipe(
          tap(res => {
            const dto = res as IPageable<IMetricDTO>;
            dto.page.sort(ICoverageModel.sortExpected);
          }),
          map(res => {
            const dto = res as IPageable<IMetricDTO>;
            const result: IPageable<IMetricModel> = {
              paging: { ...dto.paging },
              page: dto.page.map(IMetricModel.toModel)
            };

            return result;
          })
        )
    );
  }

  public historical(
    componentId: number,
    page: number = 0,
    ps: number = METRIC_HISTORY_ELEMENTS
  ): Promise<IHistoricalCoverage<IComponentModel>> {
    let httpParams = new HttpParams();

    httpParams = httpParams.append('page', String(page));
    httpParams = httpParams.append('ps', String(ps));

    return firstValueFrom(
      this.http
        .get<IHistoricalCoverage<IComponentDTO>>(`${this._urlComponents}/component/historical/${componentId}`, {
          clientOptions: { params: httpParams },
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Notifications.HistoricalNotFound' }
          }
        })
        .pipe(
          map(res => {
            const dto = res as IHistoricalCoverage<IComponentDTO>;
            const result: IHistoricalCoverage<IComponentModel> = {
              data: IComponentModel.toModel(dto.data),
              historical: dto.historical
            };

            return result;
          })
        )
    );
  }

  public clearSquadCache = (squadId: number): void => this.cache.delete(this.squadCacheId(squadId));

  private squadCacheId = (squadId: number): string => `${_COMPONENT_CACHE_ID_}_SQ_${squadId}_`;
}
