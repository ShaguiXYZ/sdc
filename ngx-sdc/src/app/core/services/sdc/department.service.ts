import { Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UniqueIds } from '../../lib';
import { IDepartmentDTO, IDepartmentModel, IPageable } from '../../models/sdc';
import { HttpStatus, UiHttpService } from '../http';
import { UiCacheService } from '../context-data';

const _DEPARTMENT_CACHE_ID_ = `_${UniqueIds.next()}_`;
const SCHEDULER_TIME = 30 * 60 * 1000;

@Injectable({ providedIn: 'root' })
export class DepartmentService {
  private _urlDepartments = `${environment.baseUrl}/api`;

  constructor(private cache: UiCacheService, private http: UiHttpService) {}

  public departments(): Promise<IPageable<IDepartmentModel>> {
    return firstValueFrom(
      this.http
        .get<IPageable<IDepartmentDTO>>(`${this._urlDepartments}/departments`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { text: 'Notifications.DepartmentsNotFound' }
          },
          cache: { id: _DEPARTMENT_CACHE_ID_, cachedDuring: SCHEDULER_TIME }
        })
        .pipe(
          map(res => {
            const dto = res as IPageable<IDepartmentDTO>;
            const result: IPageable<IDepartmentModel> = {
              paging: { ...dto.paging },
              page: dto.page.map(IDepartmentModel.toModel)
            };

            return result;
          })
        )
    );
  }

  public clearCache = () => this.cache.delete(_DEPARTMENT_CACHE_ID_);
}
