import { Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UiCacheService, UiHttpService } from '..';
import { hasValue, sortCoverageData } from '../../lib';
import { IDepartmentModel, IPageable, ISquadDTO, ISquadModel } from '../../models/sdc';
import { HttpStatus } from '../http';
import { S_EXPIRATON_TIME, _SQUADS_CACHE_ID_ } from './constants';

@Injectable({ providedIn: 'root' })
export class SquadService {
  private _urlSquads = `${environment.baseUrl}/api`;

  constructor(private cache: UiCacheService, private http: UiHttpService) {}

  public squads(department?: IDepartmentModel): Promise<IPageable<ISquadModel>> {
    return firstValueFrom(
      this.http
        .get<IPageable<ISquadDTO>>(`${this._urlSquads}/squads`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Notifications.SquadsNotFound' }
          },
          cache: { id: _SQUADS_CACHE_ID_, cachedDuring: S_EXPIRATON_TIME }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<ISquadDTO>;
            const result: IPageable<ISquadModel> = {
              paging: { ...dto.paging },
              page: dto.page
                .map(ISquadModel.toModel)
                .filter(data => hasValue(data.coverage))
                .filter(data => !department || data.department.id === department.id)
                .sort(sortCoverageData)
            };

            return result;
          })
        )
    );
  }

  public clearCache = () => this.cache.delete(_SQUADS_CACHE_ID_);
}
