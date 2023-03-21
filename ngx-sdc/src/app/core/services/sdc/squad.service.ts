import { Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { HttpStatus } from 'src/app/core/constants/app.constants';
import { environment } from 'src/environments/environment';
import { UiHttpService } from '..';
import { UniqueIds } from '../../lib';
import {
  IComponentDTO,
  IComponentModel,
  IDepartmentModel,
  IMetricAnalysisStateDTO,
  IMetricAnalysisStateModel,
  IPageableDTO,
  IPageableModel,
  IPagingModel,
  ISquadDTO,
  ISquadModel
} from '../../models/sdc';

const _SQUADS_CACHE_ID_ = `_${UniqueIds.next()}_`;

@Injectable({ providedIn: 'root' })
export class SquadService {
  private _urlSquads = `${environment.baseUrl}/api`;

  constructor(private http: UiHttpService) {}

  public squad(squadId: number, showLoading?: boolean): Promise<ISquadModel> {
    return firstValueFrom(
      this.http
        .get<ISquadDTO>(`${this._urlSquads}/squad/${squadId}`, {
          showLoading,
          responseStatusMessage: {
            [HttpStatus.notFound]: { message: 'Notifications.SquadNotFound' }
          }
        })
        .pipe(map(state => ISquadModel.toModel(state as ISquadDTO)))
    );
  }

  public squads(department: IDepartmentModel): Promise<IPageableModel<ISquadModel>> {
    return firstValueFrom(
      this.http
        .get<IPageableDTO<ISquadDTO>>(`${this._urlSquads}/squads/${department.id}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { message: 'Notifications.SquadsNotFound' },
          },
          cache: _SQUADS_CACHE_ID_
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
        .get<IMetricAnalysisStateDTO>(`${this._urlSquads}/squad/${squadId}/state`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { message: 'Notifications.SquadNotFound' }
          }
        })
        .pipe(map(state => IMetricAnalysisStateModel.toModel(state as IMetricAnalysisStateDTO)))
    );
  }

  public squadComponents(squadId: number, page: number = 0): Promise<IPageableModel<IComponentModel>> {
    return firstValueFrom(
      this.http
        .get<IPageableDTO<IComponentDTO>>(`${this._urlSquads}/squads/${squadId}/components?page=${page}`, {
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
