import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom, map, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ELEMENTS_BY_PAGE } from '../../constants/app.constants';
import { UniqueIds, deepCopy, hasValue } from '../../lib';
import { IComponentDTO, IComponentModel, IMetricDTO, IMetricModel, IPageable } from '../../models/sdc';
import { IHistoricalCoverage } from '../../models/sdc/historical-coverage.model';
import { UiCacheService } from '../context-data';
import { HttpStatus, UiHttpService } from '../http';

const _COMPONENT_CACHE_ID_ = `_${UniqueIds.next()}_`;
const SCHEDULER_TIME = 1 * 60 * 1000;

@Injectable({ providedIn: 'root' })
export class ComponentService {
  private _urlComponents = `${environment.baseUrl}/api`;

  constructor(private cache: UiCacheService, private http: UiHttpService) {}

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
          cache: { id: this.squadCacheId(squadId), cachedDuring: SCHEDULER_TIME }
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

  public filter(
    name?: string,
    squadId?: number,
    coverageMin?: number,
    coverageMax?: number,
    page?: number,
    ps: number = ELEMENTS_BY_PAGE
  ): Promise<IPageable<IComponentModel>> {
    let httpParams = new HttpParams();

    if (hasValue(page)) {
      httpParams = httpParams.append('page', String(page));
      httpParams = httpParams.append('ps', String(ps));
    }

    if (name) {
      httpParams = httpParams.append('name', name);
    }

    if (hasValue(squadId)) {
      httpParams = httpParams.append('squadId', String(squadId));
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
          showLoading: true,
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
            dto.page.sort((a, b) => (a.name > b.name ? 1 : b.name > a.name ? -1 : 0));
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

  public historical(componentId: number): Promise<IHistoricalCoverage<IComponentModel>> {
    return firstValueFrom(
      this.http
        .get<IHistoricalCoverage<IComponentDTO>>(`${this._urlComponents}/component/historical/${componentId}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Notifications.HistoricalNotFound' }
          }
        })
        .pipe(
          map(res => {
            const dto = res as IHistoricalCoverage<IComponentDTO>;
            const result: IHistoricalCoverage<IComponentModel> = {
              data: IComponentModel.toModel(dto.data),
              historical: deepCopy(dto.historical)
            };

            return result;
          })
        )
    );
  }

  public clearSquadCache = (squadId: number): void => this.cache.delete(this.squadCacheId(squadId));

  private squadCacheId = (squadId: number): string => `${_COMPONENT_CACHE_ID_}_SQ_${squadId}_`;
}
