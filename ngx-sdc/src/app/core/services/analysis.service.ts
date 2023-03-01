import { Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { HttpStatus } from 'src/app/shared/config/app.constants';
import { environment } from 'src/environments/environment';
import { UiHttpHelper } from '.';
import {
  IComponentStateDTO,
  IComponentStateModel,
  IMetricAnalysisDTO,
  IMetricAnalysisModel,
  IPageableDTO,
  IPageableModel,
  IPagingModel
} from '../models';

@Injectable({ providedIn: 'root' })
export class AnalysisService {
  private _urlAnalysis = `${environment.baseUrl}/analysis`;

  constructor(private http: UiHttpHelper) {}

  public componentState(componentId: number): Promise<IComponentStateModel> {
    return firstValueFrom(
      this.http
        .get<IComponentStateDTO>(`${this._urlAnalysis}/${componentId}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { message: 'Notifications.ComonentNotFound' }
          }
        })
        .pipe(
          map(state => {
            return IComponentStateModel.toModel(state as IComponentStateDTO);
          })
        )
    );
  }

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
            let result: IPageableModel<IMetricAnalysisModel> = {
              paging: IPagingModel.toModel(dto.paging),
              page: dto.page.map(IMetricAnalysisModel.toModel)
            };

            return result;
          })
        )
    );
  }
}
