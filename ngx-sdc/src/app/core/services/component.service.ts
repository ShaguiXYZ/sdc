import { Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { HttpStatus } from 'src/app/shared/config/app.constants';
import { environment } from 'src/environments/environment';
import { UiHttpHelper } from '.';
import { IMetricAnalysisStateDTO, IMetricAnalysisStateModel } from '../models';

@Injectable({ providedIn: 'root' })
export class ComponentService {
  private _urlComponents = `${environment.baseUrl}/component`;

  constructor(private http: UiHttpHelper) {}

  public componentState(componentId: number): Promise<IMetricAnalysisStateModel> {
    return firstValueFrom(
      this.http
        .get<IMetricAnalysisStateDTO>(`${this._urlComponents}/${componentId}/state`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { message: 'Notifications.ComonentNotFound' }
          }
        })
        .pipe(
          map(state => {
            return IMetricAnalysisStateModel.toModel(state as IMetricAnalysisStateDTO);
          })
        )
    );
  }
}
