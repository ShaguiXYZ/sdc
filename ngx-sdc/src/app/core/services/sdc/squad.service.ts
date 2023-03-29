import { Injectable } from '@angular/core';
import { firstValueFrom, map, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UiHttpService } from '..';
import { ELEMENTS_BY_PAGE } from '../../constants/app.constants';
import { UniqueIds } from '../../lib';
import {
  IComponentDTO,
  IComponentModel,
  IDepartmentModel,
  IPageableDTO,
  IPageableModel,
  IPagingModel,
  ISquadDTO,
  ISquadModel
} from '../../models/sdc';
import { HttpStatus } from '../http';

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

  public squads(department?: IDepartmentModel): Promise<IPageableModel<ISquadModel>> {
    const path = department ? `/${department.id}` : '';

    return firstValueFrom(
      this.http
        .get<IPageableDTO<ISquadDTO>>(`${this._urlSquads}/squads${path}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { message: 'Notifications.SquadsNotFound' }
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

  public squadComponents(squadId: number, page?: number, ps: number = ELEMENTS_BY_PAGE): Promise<IPageableModel<IComponentModel>> {
    let requestParms = '';

    if (page) {
      requestParms = `?page=${page}&ps=${ps}`;
    }

    return firstValueFrom(
      this.http
        .get<IPageableDTO<IComponentDTO>>(`${this._urlSquads}/squad/${squadId}/components${requestParms}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { message: 'Notifications.SquadsNotFound' }
          }
        })
        .pipe(
          tap(res => {
            const dto = res as IPageableDTO<IComponentDTO>;
            dto.page.sort((a, b) => (a.name > b.name ? 1 : b.name > a.name ? -1 : 0));
          }),
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
