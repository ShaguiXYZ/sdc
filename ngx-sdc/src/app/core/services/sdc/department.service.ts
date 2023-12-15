import { Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { hasValue } from '../../lib';
import { ICoverageModel, IDepartmentDTO, IDepartmentModel, IPageable } from '../../models/sdc';
import { CacheService } from '../context-data';
import { HttpStatus, HttpService } from '../http';
import { L_EXPIRATON_TIME, _DEPARTMENT_CACHE_ID_ } from './constants';

@Injectable({ providedIn: 'root' })
export class DepartmentService {
  private _urlDepartments = `${environment.baseUrl}/api`;

  constructor(
    private cache: CacheService,
    private http: HttpService
  ) {}

  public departments(): Promise<IPageable<IDepartmentModel>> {
    return firstValueFrom(
      this.http
        .get<IPageable<IDepartmentDTO>>(`${this._urlDepartments}/departments`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Notifications.DepartmentsNotFound' }
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
                .map(IDepartmentModel.toModel)
                .sort(ICoverageModel.sortExpected)
            };

            return result;
          })
        )
    );
  }

  public clearCache = () => this.cache.delete(_DEPARTMENT_CACHE_ID_);
}
