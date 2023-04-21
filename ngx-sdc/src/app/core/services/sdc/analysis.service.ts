import { Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UiHttpService } from '..';
import { IMetricAnalysisDTO, IMetricAnalysisModel, IPageable } from '../../models/sdc';
import { HttpStatus } from '../http';

@Injectable({ providedIn: 'root' })
export class AnalysisService {
  private _urlAnalysis = `${environment.baseUrl}/api/analysis`;

  constructor(private http: UiHttpService) {}

  public analysis(componentId: number, metricId: number): Promise<IMetricAnalysisModel> {
    return firstValueFrom(
      this.http
        .get<IMetricAnalysisDTO>(`${this._urlAnalysis}/get/${componentId}/${metricId}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { message: 'Notifications.AnalysisNotFound' }
          }
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
            [HttpStatus.notFound]: { message: 'Notifications.MetricAbalysisNotFound' }
          }
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
      this.http.post<IPageable<IMetricAnalysisDTO>, any>(`${this._urlAnalysis}/${componentId}`, undefined, {
        successMessage: { message: 'Label.Component.Analized' }
      }).pipe(
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
}
