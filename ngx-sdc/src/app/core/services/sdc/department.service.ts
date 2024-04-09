import { Injectable } from '@angular/core';
import { CacheService, HttpService, HttpStatus, hasValue } from '@shagui/ng-shagui/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ICoverageModel, IDepartmentDTO, IDepartmentModel, IPageable } from '../../models/sdc';
import { L_EXPIRATON_TIME, _DEPARTMENT_CACHE_ID_ } from './constants';
import { TranslateService } from '@ngx-translate/core';

@Injectable({ providedIn: 'root' })
export class DepartmentService {
  private _urlDepartments = `${environment.baseUrl}/api`;

  constructor(
    private readonly cache: CacheService,
    private readonly http: HttpService,
    private readonly translate: TranslateService
  ) {}

  public department(id: number): Promise<IDepartmentModel> {
    return firstValueFrom(
      this.http
        .get<IDepartmentDTO>(`${this._urlDepartments}/department/${id}`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: this.translate.instant('Notifications.DepartmentNotFound') }
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
            [HttpStatus.notFound]: { text: this.translate.instant('Notifications.DepartmentsNotFound') }
          },
          cache: { id: _DEPARTMENT_CACHE_ID_, ttl: L_EXPIRATON_TIME }
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

  public clearCache = () => this.cache.delete(_DEPARTMENT_CACHE_ID_);
}
