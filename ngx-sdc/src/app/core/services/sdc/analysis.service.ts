import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { METRIC_HISTORY_ELEMENTS } from 'src/app/shared/constants';
import { environment } from 'src/environments/environment';
import { CacheService, HttpService } from '..';
import { hasValue } from '../../lib';
import { AnalysisType, ICoverageModel, IMetricAnalysisDTO, IMetricAnalysisModel, IPageable } from '../../models/sdc';
import { HttpStatus } from '../http';
import { L_EXPIRATON_TIME, XL_EXPIRATON_TIME, _METRICS_CACHE_ID_ } from './constants';
import { DataInfo } from '../../models';

@Injectable({ providedIn: 'root' })
export class AnalysisService {
  private _urlAnalysis = `${environment.baseUrl}/api/analysis`;

  constructor(
    private cache: CacheService,
    private http: HttpService
  ) {}

  public componentAnalysis(componentId: number): Promise<IPageable<IMetricAnalysisModel>> {
    return firstValueFrom(
      this.http
        .get<IPageable<IMetricAnalysisDTO>>(`${this._urlAnalysis}/get/${componentId}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Notifications.AnalysisNotFound' }
          },
          cache: { id: this.analysisCacheId(componentId), ttl: L_EXPIRATON_TIME }
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
            [HttpStatus.notFound]: { text: 'Notifications.AnalysisNotFound' }
          },
          cache: { id: this.analysisCacheId(componentId, metricId), ttl: L_EXPIRATON_TIME }
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
    let httpParams = new HttpParams();

    httpParams = httpParams.append('page', String(page));
    httpParams = httpParams.append('ps', String(ps));

    return firstValueFrom(
      this.http
        .get<IPageable<IMetricAnalysisDTO>>(`${this._urlAnalysis}/${componentId}/${metricId}`, {
          clientOptions: { params: httpParams },
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Notifications.MetricAbalysisNotFound' }
          },
          cache: { id: this.historyCacheId(componentId, metricId), ttl: L_EXPIRATON_TIME }
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
    let httpParams = new HttpParams();
    httpParams = httpParams.append('metricName', metricName);
    httpParams = httpParams.append('metricType', metricType);

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
            ttl: XL_EXPIRATON_TIME
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

  public analize(componentId: number, onError?: DataInfo<(error: any) => void>): Promise<IPageable<IMetricAnalysisModel>> {
    return firstValueFrom(
      this.http
        .post<IPageable<IMetricAnalysisDTO>, any>(`${this._urlAnalysis}/${componentId}`, undefined, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Error.404' },
            [HttpStatus.locked]: { text: 'Error.423', fn: onError?.[HttpStatus.locked] },
            [HttpStatus.unauthorized]: { text: 'Error.401', fn: onError?.[HttpStatus.unauthorized] }
          },
          successMessage: { text: 'Notifications.ComponentAnalized' }
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
