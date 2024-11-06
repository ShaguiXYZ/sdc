import { HttpParams, HttpStatusCode } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { CacheService, hasValue, HttpService, TTL } from '@shagui/ng-shagui/core';
import { firstValueFrom, map, tap } from 'rxjs';
import { METRIC_HISTORY_ELEMENTS } from 'src/app/shared/constants';
import { environment } from 'src/environments/environment';
import { IComponentDTO, IComponentModel, ICoverageModel, IMetricDTO, IMetricModel, IPageable } from '../../models/sdc';
import { IHistoricalCoverage } from '../../models/sdc/historical-coverage.model';
import { _COMPONENT_CACHE_ID_ } from './constants';

@Injectable({ providedIn: 'root' })
export class ComponentService {
  private _urlComponents = `${environment.baseUrl}/api`;

  private readonly cache = inject(CacheService);
  private readonly http = inject(HttpService);

  constructor(private readonly translate: TranslateService) {}

  public component(componentId: number): Promise<IComponentModel> {
    return firstValueFrom(
      this.http
        .get<IComponentDTO>(`${this._urlComponents}/component/${componentId}`, {
          responseStatusMessage: {
            [HttpStatusCode.NotFound]: { text: this.translate.instant('Notifications.ComponentNotFound') }
          }
        })
        .pipe(map(res => IComponentModel.fromDTO(res as IComponentDTO)))
    );
  }

  public squadComponents(squadId: number): Promise<IPageable<IComponentModel>> {
    return firstValueFrom(
      this.http
        .get<IPageable<IComponentDTO>>(`${this._urlComponents}/components/squad/${squadId}`, {
          showLoading: true,
          responseStatusMessage: {
            [HttpStatusCode.NotFound]: { text: this.translate.instant('Notifications.ComponentsNotFound') }
          },
          cache: { id: this.squadCacheId(squadId), ttl: TTL.XS }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<IComponentDTO>;
            const result: IPageable<IComponentModel> = {
              paging: { ...dto.paging },
              page: dto.page.filter(data => hasValue(data.coverage)).map(IComponentModel.fromDTO)
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
            [HttpStatusCode.NotFound]: { text: this.translate.instant('Notifications.ComponentsNotFound') }
          }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<IComponentDTO>;
            const result: IPageable<IComponentModel> = {
              paging: { ...dto.paging },
              page: dto.page.map(IComponentModel.fromDTO)
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
            [HttpStatusCode.NotFound]: { text: this.translate.instant('Notifications.MetricsNotFound') }
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
              page: dto.page.map(IMetricModel.fromDTO)
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
    const httpParams = new HttpParams().appendAll({ page, ps });

    return firstValueFrom(
      this.http
        .get<IHistoricalCoverage<IComponentDTO>>(`${this._urlComponents}/component/historical/${componentId}`, {
          clientOptions: { params: httpParams },
          responseStatusMessage: {
            [HttpStatusCode.NotFound]: { text: this.translate.instant('Notifications.HistoricalNotFound') }
          }
        })
        .pipe(
          map(res => {
            const dto = res as IHistoricalCoverage<IComponentDTO>;
            const result: IHistoricalCoverage<IComponentModel> = {
              data: IComponentModel.fromDTO(dto.data),
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
