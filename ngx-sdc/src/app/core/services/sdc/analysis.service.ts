import { Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UiHttpService } from '..';
import { IMetricAnalysisDTO, IMetricAnalysisModel, IPageableDTO, IPageableModel, IPagingModel } from '../../models/sdc';
import { HttpStatus } from '../http';

@Injectable({ providedIn: 'root' })
export class AnalysisService {
  private _urlAnalysis = `${environment.baseUrl}/api/analysis`;

  constructor(private http: UiHttpService) {}

  public metricHistory(componentId: number, metricId: number): Promise<IPageableModel<IMetricAnalysisModel>> {
    return firstValueFrom(
      this.http
        .get<IPageableDTO<IMetricAnalysisDTO>>(`${this._urlAnalysis}/${componentId}/${metricId}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { message: 'Notifications.SquadsNotFound' }
          }
        })
        .pipe(
          map(res => {
            const dto = res as IPageableDTO<IMetricAnalysisDTO>;
            const result: IPageableModel<IMetricAnalysisModel> = {
              paging: IPagingModel.toModel(dto.paging),
              page: dto.page.map(IMetricAnalysisModel.toModel)
            };

            return result;
          })
        )
    );
  }
}