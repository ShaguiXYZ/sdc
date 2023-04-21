import { Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UiHttpService } from '..';
import { UniqueIds } from '../../lib';
import { IDepartmentModel, IPageable, ISquadDTO, ISquadModel } from '../../models/sdc';
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
        .pipe(map(data => ISquadModel.toModel(data as ISquadDTO)))
    );
  }

  public squads(department?: IDepartmentModel): Promise<IPageable<ISquadModel>> {
    const path = department ? `/${department.id}` : '';

    return firstValueFrom(
      this.http
        .get<IPageable<ISquadDTO>>(`${this._urlSquads}/squads${path}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { message: 'Notifications.SquadsNotFound' }
          },
          cache: _SQUADS_CACHE_ID_
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<ISquadDTO>;
            const result: IPageable<ISquadModel> = {
              paging: { ...dto.paging },
              page: dto.page.map(ISquadModel.toModel)
            };

            return result;
          })
        )
    );
  }
}
