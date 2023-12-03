import { Inject, Injectable, OnDestroy, Optional } from '@angular/core';
import { Observable, firstValueFrom, of, take } from 'rxjs';
import { emptyFn, hasValue, _console } from '../../lib';
import { SchedulerService } from '../task-scheduler';
import { MIN_CACHE_SCHEDULER_PERIOD, NX_CONTEX_CONFIG } from './constatnts';
import { ContextDataService } from './context-data.service';
import { ContextConfig } from './models';

/**
 * Cache
 */
@Injectable({
  providedIn: 'root'
})
export class CacheService implements OnDestroy {
  private schedukerId!: string;
  private schedulerPeriod!: number;

  constructor(
    @Optional() @Inject(NX_CONTEX_CONFIG) contextConfig: ContextConfig,
    private readonly contextData: ContextDataService,
    private readonly scheduleroService: SchedulerService
  ) {
    this.schedulerPeriod =
      contextConfig?.cache?.schedulerPeriod && contextConfig.cache.schedulerPeriod > MIN_CACHE_SCHEDULER_PERIOD
        ? contextConfig.cache.schedulerPeriod
        : MIN_CACHE_SCHEDULER_PERIOD;

    this.schedukerId = this.scheduleroService.create(this.resetContextData, undefined, this.schedulerPeriod);
    this.scheduleroService.subscribe(this.schedukerId, emptyFn);
  }

  ngOnDestroy(): void {
    this.scheduleroService.finish(this.schedukerId);
  }

  public expirationDate = (cachedDuring?: number) => (hasValue(cachedDuring) ? new Date().getTime() + (cachedDuring ?? 0) : undefined);

  public set(key: string, data: any, expiration?: number) {
    this.contextData.cache[key] = { data, expiration };

    _console.log(`cache data ${key} added`, {
      'expited-on': expiration ? `${new Date(expiration)}` : 'never',
      data
    });
  }

  public get(key: string): any {
    const data = this.contextData.cache[key];

    _console.log(`Retrieving cache data ${key}`, data);

    return data && (!data.expiration || data.expiration > new Date().getTime()) ? data.data : undefined;
  }

  public delete = (key: string | RegExp): void => {
    if (typeof key === 'string') {
      _console.log(`deleting ${key} from cache`);
      delete this.contextData.cache[key];
    } else {
      const keys = Object.keys(this.contextData.cache);
      keys.filter(k => key.test(k)).forEach(this.delete);
    }
  };

  public asObservable<T>(key: string): Observable<T> {
    return of(this.get(key)).pipe(take(1));
  }

  public asPromise<T>(key: string): Promise<T> {
    return firstValueFrom(this.asObservable(key));
  }

  private resetContextData = (): void =>
    Object.keys(this.contextData.cache)
      .filter(key => this.contextData.cache[key].expiration && (this.contextData.cache[key].expiration ?? 0) < new Date().getTime())
      .forEach(this.delete);
}
