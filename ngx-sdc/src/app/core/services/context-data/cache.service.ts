import { Inject, Injectable, OnDestroy, Optional } from '@angular/core';
import { Observable, firstValueFrom, of, take } from 'rxjs';
import { GenericDataInfo } from '../../interfaces/dataInfo';
import { emptyFn, hasValue } from '../../lib';
import { UiSchedulerService } from '../task-scheduler';
import { UiContextDataService } from './context-data.service';
import { ContextConfig, NX_CONTEX_CONFIG } from './models';

const MIN_SCHEDULER_PERIOD = 30 * 1000;

/**
 * Cache
 */
@Injectable({
  providedIn: 'root'
})
export class UiCacheService implements OnDestroy {
  private schedukerId!: string;
  private scheduledIds: GenericDataInfo<number> = {}; // 0 to clear the cache ID each time the scheduler is executed or the date to clear

  private schedulerPeriod!: number;

  constructor(
    @Optional() @Inject(NX_CONTEX_CONFIG) contextConfig: ContextConfig,
    private contextData: UiContextDataService,
    private scheduleroService: UiSchedulerService
  ) {
    this.schedulerPeriod = contextConfig.cache?.schedulerPeriod
      ? contextConfig.cache.schedulerPeriod < MIN_SCHEDULER_PERIOD
        ? MIN_SCHEDULER_PERIOD
        : contextConfig.cache.schedulerPeriod
      : MIN_SCHEDULER_PERIOD;

    this.schedukerId = this.scheduleroService.create(this.resetContextData, undefined, this.schedulerPeriod);
    this.scheduleroService.subscribe(this.schedukerId, emptyFn);
  }

  ngOnDestroy(): void {
    this.scheduleroService.finish(this.schedukerId);
  }

  public expirationDate = (cachedDuring?: number) => (hasValue(cachedDuring) ? new Date().getTime() + (cachedDuring || 0) : undefined);

  public add(key: string, data: any, expiration?: number) {
    this.contextData.cache[key] = data;

    if (expiration !== undefined) {
      this.scheduledIds[key] = expiration;
    }

    console.log(`cache data ${key} added`, {
      'expited-on': this.scheduledIds[key] ? `${new Date(this.scheduledIds[key])}` : 'never',
      data
    });
  }

  public get(key: string): any {
    const data = this.contextData.cache[key];
    console.log(`Retrieving cache data ${key}`, data);

    return hasValue(this.scheduledIds[key]) && this.scheduledIds[key] > new Date().getTime() ? data : undefined;
  }

  public delete(key: string) {
    delete this.contextData.cache[key];
    delete this.scheduledIds[key];
  }

  public asObservable<T>(key: string): Observable<T> {
    return of(this.get(key)).pipe(take(1));
  }

  public asPromise<T>(key: string): Promise<T> {
    return firstValueFrom(this.asObservable(key));
  }

  private resetContextData = (): void =>
    Object.keys(this.scheduledIds)
      .filter(key => this.scheduledIds[key] < new Date().getTime())
      .forEach(key => {
        console.log(`deleting ${key} from cache`);

        this.delete(key);
      });
}
