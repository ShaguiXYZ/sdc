import { HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { hasValue, HttpService } from '@shagui/ng-shagui/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IPageable, ISummaryViewDTO, ISummaryViewModel, SummaryViewType } from '../../models/sdc';

@Injectable({ providedIn: 'root' })
export class SummaryViewService {
  private _urlSummaryView = `${environment.baseUrl}/api/summary`;

  private readonly http = inject(HttpService);

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
