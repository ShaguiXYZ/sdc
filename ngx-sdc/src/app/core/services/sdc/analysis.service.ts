import { Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UiHttpService } from '..';
import { IMetricAnalysisDTO, IMetricAnalysisModel, IPageable } from '../../models/sdc';
import { HttpStatus } from '../http';
import { METRICS_EXPIRATON_TIME, _METRICS_CACHE_ID_ } from './constants';

@Injectable({ providedIn: 'root' })
export class AnalysisService {
  private _urlAnalysis = `${environment.baseUrl}/api/analysis`;

  constructor(private http: UiHttpService) {}

  public analysis(componentId: number, metricId: number): Promise<IMetricAnalysisModel> {
    return firstValueFrom(
      this.http
        .get<IMetricAnalysisDTO>(`${this._urlAnalysis}/get/${componentId}/${metricId}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Notifications.AnalysisNotFound' }
          },
          cache: { id: this.analysisCacheId(componentId, metricId), cachedDuring: METRICS_EXPIRATON_TIME }
        })
        .pipe(
          map(res => {
            const dto = res as IMetricAnalysisDTO;
            const result: IMetricAnalysisModel = IMetricAnalysisModel.toModel(dto);

            return result;
          })
        )
    );
  }

  public metricHistory(componentId: number, metricId: number): Promise<IPageable<IMetricAnalysisModel>> {
    return firstValueFrom(
      this.http
        .get<IPageable<IMetricAnalysisDTO>>(`${this._urlAnalysis}/${componentId}/${metricId}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Notifications.MetricAbalysisNotFound' }
          },
          cache: { id: this.historyCacheId(componentId, metricId), cachedDuring: METRICS_EXPIRATON_TIME }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<IMetricAnalysisDTO>;
            const result: IPageable<IMetricAnalysisModel> = {
              paging: { ...dto.paging },
              page: dto.page.map(IMetricAnalysisModel.toModel)
            };

            return result;
          })
        )
    );
  }

  public analize(componentId: number): Promise<IPageable<IMetricAnalysisModel>> {
    return firstValueFrom(
      this.http
        .post<IPageable<IMetricAnalysisDTO>, any>(`${this._urlAnalysis}/${componentId}`, undefined, {
          successMessage: { text: 'Label.Component.Analized' }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<IMetricAnalysisDTO>;
            const result: IPageable<IMetricAnalysisModel> = {
              paging: { ...dto.paging },
              page: dto.page.map(IMetricAnalysisModel.toModel)
            };

            return result;
          })
        )
    );
  }

  private analysisCacheId = (componentId: number, metricId: number): string => `${_METRICS_CACHE_ID_}_AN_${componentId}_${metricId}_`;
  private historyCacheId = (componentId: number, metricId: number): string => `${_METRICS_CACHE_ID_}_HT_${componentId}_${metricId}_`;
}
