import { Injectable } from '@angular/core';
import { CacheService, HttpService, HttpStatus, hasValue } from '@shagui/ng-shagui/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ICoverageModel, IDepartmentModel, IPageable, ISquadDTO, ISquadModel } from '../../models/sdc';
import { S_EXPIRATON_TIME, _SQUADS_CACHE_ID_ } from './constants';
import { TranslateService } from '@ngx-translate/core';

@Injectable({ providedIn: 'root' })
export class SquadService {
  private _urlSquads = `${environment.baseUrl}/api`;

  constructor(
    private readonly cache: CacheService,
    private readonly http: HttpService,
    private readonly translate: TranslateService
  ) {}

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
          cache: { id: _SQUADS_CACHE_ID_, ttl: S_EXPIRATON_TIME }
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
