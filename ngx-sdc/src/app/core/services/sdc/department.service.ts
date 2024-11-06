import { HttpStatusCode } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { CacheService, DataInfo, hasValue, HttpService, TTL } from '@shagui/ng-shagui/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ICoverageModel, IDepartmentDTO, IDepartmentModel, IPageable } from '../../models/sdc';
import { _DEPARTMENT_CACHE_ID_ } from './constants';

@Injectable({ providedIn: 'root' })
export class DepartmentService {
  private _urlDepartments = `${environment.baseUrl}/api`;

  private readonly cache = inject(CacheService);
  private readonly http = inject(HttpService);

  constructor(private readonly translate: TranslateService) {}

  public department(id: number): Promise<IDepartmentModel> {
    return firstValueFrom(
      this.http
        .get<IDepartmentDTO>(`${this._urlDepartments}/department/${id}`, {
          responseStatusMessage: {
            [HttpStatusCode.NotFound]: { text: this.translate.instant('Notifications.DepartmentNotFound') }
          }
        })
        .pipe(map(res => IDepartmentModel.fromDTO(res as IDepartmentDTO)))
    );
  }

  public departments(): Promise<IPageable<IDepartmentModel>> {
    return firstValueFrom(
      this.http
        .get<IPageable<IDepartmentDTO>>(`${this._urlDepartments}/departments`, {
          responseStatusMessage: {
            [HttpStatusCode.NotFound]: { text: this.translate.instant('Notifications.DepartmentsNotFound') }
          },
          cache: { id: _DEPARTMENT_CACHE_ID_, ttl: TTL.L }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<IDepartmentDTO>;
            const result: IPageable<IDepartmentModel> = {
              paging: { ...dto.paging },
              page: dto.page
                .filter(data => hasValue(data.coverage))
                .map(IDepartmentModel.fromDTO)
                .sort(ICoverageModel.sortExpected)
            };

            return result;
          })
        )
    );
  }

  public updateDeparments(onError?: DataInfo<(error: unknown) => void>): Promise<IDepartmentModel[]> {
    return firstValueFrom(
      this.http
        .post<undefined, IDepartmentDTO[]>(`${this._urlDepartments}/departments/update`, undefined, {
          responseStatusMessage: {
            [HttpStatusCode.NotFound]: { text: this.translate.instant('Error.404') },
            [HttpStatusCode.Locked]: { text: this.translate.instant('Error.423'), fn: onError?.[HttpStatusCode.Locked] },
            [HttpStatusCode.Unauthorized]: { text: this.translate.instant('Error.401'), fn: onError?.[HttpStatusCode.Unauthorized] }
          },
          procesingMessage: { text: this.translate.instant('Notifications.UpdatingServerInfo') },
          successMessage: { text: this.translate.instant('Notifications.ServerInfoUpdated') }
        })
        .pipe(
          map(res => {
            const dto = res as IDepartmentDTO[];
            return dto.map(IDepartmentModel.fromDTO);
          })
        )
    );
  }

  public clearCache = () => this.cache.delete(_DEPARTMENT_CACHE_ID_);
}
