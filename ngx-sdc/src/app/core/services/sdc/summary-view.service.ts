import { Injectable } from '@angular/core';
import { HttpService, HttpStatus } from '../http';
import { environment } from 'src/environments/environment';
import { IPageable, ISummaryViewDTO, ISummaryViewModel, SummaryViewType } from '../../models/sdc';
import { HttpParams } from '@angular/common/http';
import { hasValue } from '../../lib';
import { firstValueFrom, map } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class SummaryViewService {
  private _urlSummaryView = `${environment.baseUrl}/api/summary`;

  constructor(private http: HttpService) {}

  getSummaryViews(name: string, types: SummaryViewType[] = [], page?: number, ps?: number): Promise<IPageable<ISummaryViewModel>> {
    let httpParams = new HttpParams();

    if (hasValue(page)) {
      httpParams = httpParams.append('page', String(page));
    }

    if (hasValue(ps)) {
      httpParams = httpParams.append('ps', String(ps));
    }

    if (name) {
      httpParams = httpParams.append('name', name);
    }

    if (types?.length) {
      httpParams = httpParams.append('types', types.join(','));
    }

    return firstValueFrom(
      this.http.get<IPageable<ISummaryViewDTO>>(`${this._urlSummaryView}/filter`, { clientOptions: { params: httpParams } }).pipe(
        map(res => {
          const dto = res as IPageable<ISummaryViewDTO>;
          const result: IPageable<ISummaryViewModel> = {
            paging: { ...dto.paging },
            page: dto.page.map(ISummaryViewModel.fromDTO)
          };

          return result;
        })
      )
    );
  }
}
