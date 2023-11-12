import { Injectable } from '@angular/core';
import { Observable, firstValueFrom, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UiCacheService, UiHttpService } from '..';
import { XXL_EXPIRATON_TIME, _DATA_LIST_CACHE_ID_ } from './constants';

@Injectable({ providedIn: 'root' })
export class DataListService {
  private _urlDataLists = `${environment.baseUrl}/api`;

  public availableDataLists(): Promise<string[]> {
    return firstValueFrom(
      this.http
        .get<string[]>(`${this._urlDataLists}/datalists`, {
          cache: { id: this.dataListCacheId(), cachedDuring: XXL_EXPIRATON_TIME }
        })
        .pipe(map(res => res as string[]))
    );
  }
  constructor(private cache: UiCacheService, private http: UiHttpService) {}

  public dataListValues(key: string): Observable<string[]> {
    console.log('dataListValues', key);

    return this.http
      .get<string[]>(`${this._urlDataLists}/datalist/${key}`, {
        cache: { id: this.dataListCacheId(key), cachedDuring: XXL_EXPIRATON_TIME }
      })
      .pipe(map(res => res as string[]));
  }

  private dataListCacheId = (key?: string): string => `${_DATA_LIST_CACHE_ID_}_DL_${key ?? '$availables$'}_`;
}
