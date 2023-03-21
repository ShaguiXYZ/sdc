import { Injectable } from '@angular/core';
import { firstValueFrom, Observable, of, take } from 'rxjs';
import { UiAppContextDataService } from './context-data.service';

/**
 * Cache
 */
@Injectable({
  providedIn: 'root'
})
export class UiCache {
  constructor(private contextData: UiAppContextDataService) {}

  public add(key: string, data: any) {
    this.contextData.cache[key] = data;
  }

  public get(key: string): any {
    return this.contextData.cache[key];
  }

  public delete(key: string) {
    delete this.contextData.cache[key];
  }

  public asObservable<T>(key: string): Observable<T> {
    return of(this.get(key)).pipe(take(1));
  }

  public asPromise<T>(key: string): Promise<T> {
    return firstValueFrom(this.asObservable(key));
  }
}
