import { Injectable } from '@angular/core';
import { firstValueFrom, map, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IComponentDTO, IComponentModel, IMetricDTO, IMetricModel, IPageableDTO, IPageableModel, IPagingModel } from '../../models/sdc';
import { HttpStatus, UiHttpService } from '../http';
import { ELEMENTS_BY_PAGE } from '../../constants/app.constants';
import { hasValue } from '../../lib';
import { HttpParams } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ComponentService {
  private _urlComponents = `${environment.baseUrl}/api`;

  constructor(private http: UiHttpService) {}

  public filter(
    name?: string,
    squadId?: number,
    coverageMin?: number,
    coverageMax?: number,
    page?: number,
    ps: number = ELEMENTS_BY_PAGE
  ): Promise<IPageableModel<IComponentModel>> {
    let httpParams = new HttpParams();

    if (hasValue(page)) {
      httpParams = httpParams.append('page', String(page));
      httpParams = httpParams.append('ps', String(ps));
    }

    if (name) {
      httpParams = httpParams.append('name', name);
    }

    if (squadId) {
      httpParams = httpParams.append('squadId', String(squadId));
    }

    if (hasValue(coverageMin)) {
      httpParams = httpParams.append('coverageMin', String(coverageMin));
    }

    if (coverageMax) {
      httpParams = httpParams.append('coverageMax', String(coverageMax));
    }

    return firstValueFrom(
      this.http
        .get<IPageableDTO<IComponentDTO>>(`${this._urlComponents}/components/filter`, {
          showLoading: true,
          clientOptions: { params: httpParams },
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

  public componentMetrics(componentId: number): Promise<IPageableModel<IMetricModel>> {
    return firstValueFrom(
      this.http
        .get<IPageableDTO<IMetricDTO>>(`${this._urlComponents}/component/${componentId}/metrics`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { message: 'Notifications.SquadsNotFound' }
          }
        })
        .pipe(
          tap(res => {
            const dto = res as IPageableDTO<IMetricDTO>;
            dto.page.sort((a, b) => (a.name > b.name ? 1 : b.name > a.name ? -1 : 0));
          }),
          map(res => {
            const dto = res as IPageableDTO<IMetricDTO>;
            const result: IPageableModel<IMetricModel> = {
              paging: IPagingModel.toModel(dto.paging),
              page: dto.page.map(IMetricModel.toModel)
            };

            return result;
          })
        )
    );
  }
}
