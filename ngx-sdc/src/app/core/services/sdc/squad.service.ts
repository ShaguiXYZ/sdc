import { inject, Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { CacheService, hasValue, HttpService, HttpStatus, TTL } from '@shagui/ng-shagui/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ICoverageModel, IDepartmentModel, IPageable, ISquadDTO, ISquadModel } from '../../models/sdc';
import { _SQUADS_CACHE_ID_ } from './constants';

@Injectable({ providedIn: 'root' })
export class SquadService {
  private _urlSquads = `${environment.baseUrl}/api`;

  private readonly cache = inject(CacheService);
  private readonly http = inject(HttpService);

  constructor(private readonly translate: TranslateService) {}

  public squad(id: number): Promise<ISquadModel> {
    return firstValueFrom(
      this.http
        .get<ISquadDTO>(`${this._urlSquads}/squad/${id}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: this.translate.instant('Notifications.SquadNotFound') }
          }
        })
        .pipe(map(res => ISquadModel.fromDTO(res as ISquadDTO)))
    );
  }

  public squads(department?: IDepartmentModel): Promise<IPageable<ISquadModel>> {
    return firstValueFrom(
      this.http
        .get<IPageable<ISquadDTO>>(`${this._urlSquads}/squads`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: this.translate.instant('Notifications.SquadsNotFound') }
          },
          cache: { id: _SQUADS_CACHE_ID_, ttl: TTL.S }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<ISquadDTO>;
            const result: IPageable<ISquadModel> = {
              paging: { ...dto.paging },
              page: dto.page
                .map(ISquadModel.fromDTO)
                .filter(data => hasValue(data.coverage))
                .filter(data => !department || data.department.id === department.id)
                .sort(ICoverageModel.sortExpected)
            };

            return result;
          })
        )
    );
  }

  public clearCache = () => this.cache.delete(_SQUADS_CACHE_ID_);
}
