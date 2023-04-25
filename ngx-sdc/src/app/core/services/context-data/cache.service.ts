import { Injectable, OnDestroy } from '@angular/core';
import { Observable, firstValueFrom, of, take } from 'rxjs';
import { GenericDataInfo } from '../../interfaces/dataInfo';
import { UiSchedulerService } from '../task-scheduler';
import { UiContextDataService } from './context-data.service';

const DEFAULT_SCHEDULER_PERIOD = 5 * 60 * 1000;

/**
 * Cache
 */
@Injectable({
  providedIn: 'root'
})
export class UiCacheService implements OnDestroy {
  private schedukerId!: string;
  private scheduledIds: GenericDataInfo<boolean> = {};

  constructor(private contextData: UiContextDataService, private scheduleroService: UiSchedulerService) {
    this.schedukerId = this.scheduleroService.create(this.resetContextData, undefined, DEFAULT_SCHEDULER_PERIOD);
    this.scheduleroService.subscribe(this.schedukerId, () => console.log('Sdc cache cleaned...'));
  }

  ngOnDestroy(): void {
    this.scheduleroService.finish(this.schedukerId);
  }

  public add(key: string, data: any, scheduled: boolean = false) {
    this.contextData.cache[key] = data;

    if (scheduled) {
      this.scheduledIds[key] = true;
    }
  }

  public get(key: string): any {
    return this.contextData.cache[key];
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
      .filter(key => this.scheduledIds[key])
      .forEach(key => this.delete(key));
}
