import { HttpParams, HttpStatusCode } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { CacheService, DataInfo, hasValue, HttpService, TTL } from '@shagui/ng-shagui/core';
import { firstValueFrom, map } from 'rxjs';
import { METRIC_HISTORY_ELEMENTS } from 'src/app/shared/constants';
import { environment } from 'src/environments/environment';
import { AnalysisType, ICoverageModel, IMetricAnalysisDTO, IMetricAnalysisModel, IPageable } from '../../models/sdc';
import { _METRICS_CACHE_ID_ } from './constants';

@Injectable({ providedIn: 'root' })
export class AnalysisService {
  private _urlAnalysis = `${environment.baseUrl}/api/analysis`;

  private readonly cache = inject(CacheService);
  private readonly http = inject(HttpService);

  constructor(private readonly translate: TranslateService) {}

  public componentAnalysis(componentId: number): Promise<IPageable<IMetricAnalysisModel>> {
    return firstValueFrom(
      this.http
        .get<IPageable<IMetricAnalysisDTO>>(`${this._urlAnalysis}/get/${componentId}`, {
          responseStatusMessage: {
            [HttpStatusCode.NotFound]: { text: this.translate.instant('Notifications.AnalysisNotFound') }
          },
          cache: { id: this.analysisCacheId(componentId), ttl: TTL.L }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<IMetricAnalysisDTO>;
            const result: IPageable<IMetricAnalysisModel> = {
              paging: { ...dto.paging },
              page: dto.page.map(IMetricAnalysisModel.fromDTO).sort(ICoverageModel.sortExpected)
            };

            return result;
          })
        )
    );
  }

  public analysis(componentId: number, metricId: number): Promise<IMetricAnalysisModel> {
    return firstValueFrom(
      this.http
        .get<IMetricAnalysisDTO>(`${this._urlAnalysis}/get/${componentId}/${metricId}`, {
          responseStatusMessage: {
            [HttpStatusCode.NotFound]: { text: this.translate.instant('Notifications.AnalysisNotFound') }
          },
          cache: { id: this.analysisCacheId(componentId, metricId), ttl: TTL.L }
        })
        .pipe(
          map(res => {
            const dto = res as IMetricAnalysisDTO;
            const result: IMetricAnalysisModel = IMetricAnalysisModel.fromDTO(dto);

            return result;
          })
        )
    );
  }

  public metricHistory(
    componentId: number,
    metricId: number,
    page: number = 0,
    ps: number = METRIC_HISTORY_ELEMENTS
  ): Promise<IPageable<IMetricAnalysisModel>> {
    const httpParams = new HttpParams().appendAll({ page, ps });

    return firstValueFrom(
      this.http
        .get<IPageable<IMetricAnalysisDTO>>(`${this._urlAnalysis}/${componentId}/${metricId}`, {
          clientOptions: { params: httpParams },
          responseStatusMessage: {
            [HttpStatusCode.NotFound]: { text: this.translate.instant('Notifications.MetricAbalysisNotFound') }
          },
          cache: { id: this.historyCacheId(componentId, metricId), ttl: TTL.L }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<IMetricAnalysisDTO>;
            const result: IPageable<IMetricAnalysisModel> = {
              paging: { ...dto.paging },
              page: dto.page.map(IMetricAnalysisModel.fromDTO)
            };

            return result;
          })
        )
    );
  }

  public annualSummary(
    metricName: string,
    metricType: AnalysisType,
    componentId?: number,
    squadId?: number,
    departmentId?: number
  ): Promise<IPageable<IMetricAnalysisModel>> {
    let httpParams = new HttpParams().appendAll({ metricName, metricType });

    if (hasValue(componentId)) {
      httpParams = httpParams.append('componentId', String(componentId));
    }

    if (hasValue(squadId)) {
      httpParams = httpParams.append('squadId', String(squadId));
    }

    if (hasValue(departmentId)) {
      httpParams = httpParams.append('departmentId', String(departmentId));
    }

    return firstValueFrom(
      this.http
        .get<IPageable<IMetricAnalysisDTO>>(`${this._urlAnalysis}/annualSum`, {
          clientOptions: { params: httpParams },
          cache: {
            id: this.annualSumCacheId(metricName, metricType, componentId, squadId, departmentId),
            ttl: TTL.XL
          }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<IMetricAnalysisDTO>;
            const result: IPageable<IMetricAnalysisModel> = {
              paging: { ...dto.paging },
              page: dto.page.map(IMetricAnalysisModel.fromDTO)
            };

            return result;
          })
        )
    );
  }

  public analize(componentId: number, onError?: DataInfo<(error: unknown) => void>): Promise<IPageable<IMetricAnalysisModel>> {
    return firstValueFrom(
      this.http
        .post<undefined, IPageable<IMetricAnalysisDTO>>(`${this._urlAnalysis}/${componentId}`, undefined, {
          responseStatusMessage: {
            [HttpStatusCode.NotFound]: { text: this.translate.instant('Error.404') },
            [HttpStatusCode.Locked]: { text: this.translate.instant('Error.423'), fn: onError?.[HttpStatusCode.Locked] },
            [HttpStatusCode.Unauthorized]: { text: this.translate.instant('Error.401'), fn: onError?.[HttpStatusCode.Unauthorized] }
          },
          procesingMessage: { text: this.translate.instant('Notifications.AnalyzingComponent') },
          successMessage: { text: this.translate.instant('Notifications.ComponentAnalized') }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<IMetricAnalysisDTO>;
            const result: IPageable<IMetricAnalysisModel> = {
              paging: { ...dto.paging },
              page: dto.page.map(IMetricAnalysisModel.fromDTO).sort(ICoverageModel.sortExpected)
            };

            return result;
          })
        )
    );
  }

  public clearAnalysisCache = (componentId: number): void => {
    this.cache.delete(new RegExp(`^${this.analysisCacheId(componentId)}`, 'g'));
    this.cache.delete(new RegExp(`^${this.historyCacheId(componentId)}`, 'g'));
  };

  private analysisCacheId = (componentId: number, metricId?: number): string =>
    metricId ? `${_METRICS_CACHE_ID_}_AN_${componentId}_${metricId}_` : `${_METRICS_CACHE_ID_}_AN_${componentId}_`;
  private historyCacheId = (componentId: number, metricId?: number): string =>
    metricId ? `${_METRICS_CACHE_ID_}_HT_${componentId}_${metricId}_` : `${_METRICS_CACHE_ID_}_HT_${componentId}_`;
  private annualSumCacheId = (
    metricName: string,
    metricType: AnalysisType,
    componentId?: number,
    squadId?: number,
    departmentId?: number
  ): string => `${_METRICS_CACHE_ID_}_AS_${metricName}_${metricType}_c${componentId}_s${squadId}_d${departmentId}_`;
}
