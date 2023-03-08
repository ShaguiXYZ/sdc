import { Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { HttpStatus } from 'src/app/shared/config/app.constants';
import { environment } from 'src/environments/environment';
import { UiHttpHelper } from '.';
import {
  IComponentDTO,
  IComponentModel,
  IMetricAnalysisStateDTO,
  IMetricAnalysisStateModel,
  IPageableDTO,
  IPageableModel,
  IPagingModel,
  ISquadDTO,
  ISquadModel
} from '../models';

@Injectable({ providedIn: 'root' })
export class SquadService {
  private _urlSquads = `${environment.baseUrl}/api/squads`;

  constructor(private http: UiHttpHelper) {}

  public availableSquads(): Promise<IPageableModel<ISquadModel>> {
    return firstValueFrom(
      this.http
        .get<IPageableDTO<ISquadDTO>>(`${this._urlSquads}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { message: 'Notifications.SquadsNotFound' }
          }
        })
        .pipe(
          map(res => {
            const dto = res as IPageableDTO<ISquadDTO>;
            const result: IPageableModel<ISquadModel> = {
              paging: IPagingModel.toModel(dto.paging),
              page: dto.page.map(ISquadModel.toModel)
            };

            return result;
          })
        )
    );
  }

  public squadState(squadId: number): Promise<IMetricAnalysisStateModel> {
    return firstValueFrom(
      this.http
        .get<IMetricAnalysisStateDTO>(`${this._urlSquads}/${squadId}/state`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { message: 'Notifications.SquadNotFound' }
          }
        })
        .pipe(
          map(state => {
            return IMetricAnalysisStateModel.toModel(state as IMetricAnalysisStateDTO);
          })
        )
    );
  }

  public squadComponents(squadId: number, page: number = 0): Promise<IPageableModel<IComponentModel>> {
    return firstValueFrom(
      this.http
        .get<IPageableDTO<IComponentDTO>>(`${this._urlSquads}/${squadId}/components?page=${page}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { message: 'Notifications.SquadsNotFound' }
          }
        })
        .pipe(
          map(res => {
            const dto = res as IPageableDTO<IComponentDTO>;
            const result: IPageableModel<IComponentModel> = {
              paging: IPagingModel.toModel(dto.paging),
              page: dto.page.map(IComponentModel.toModel)
            };

            return result;
          })
        )
    );
  }
}
