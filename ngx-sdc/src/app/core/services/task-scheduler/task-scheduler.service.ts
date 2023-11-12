import { Injectable } from '@angular/core';
import { Subscription } from 'rxjs';
import { UniqueIds, _console } from '../../lib';
import { TaskScheduler } from './model';

interface SchedulerSubscription {
  schedulerId: string;
  subscriptionId: string;
  subscription: Subscription;
}

@Injectable({
  providedIn: 'root'
})
export class SchedulerService {
  /**
   * <schedulerId, task scheduler>
   */
  private schedulers: Map<string, TaskScheduler<any>>;
  /**
   * <subscriptionId, <schedulerId, subscription>>
   */
  private subscription$: Map<string, SchedulerSubscription>;

  constructor() {
    this.schedulers = new Map();
    this.subscription$ = new Map();
  }

  /**
   * Schedule a task and return the associated id
   * and return the specified schedulerId or a unique schedulerId
   *
   * @param task
   * @param scheduleId
   * @param period
   * @returns scheduleId
   */
  public create<D>(task: () => D, scheduleId?: string, period?: number): string {
    const id = scheduleId ?? `${UniqueIds.next()}`;

    if (this.schedulers.get(id)) {
      this.finish(id);
    }

    this.schedulers.set(id, new TaskScheduler<D>(task, period));

    _console.log(`Scheduler ${id} created...`, this);

    return id;
  }

  /**
   * Unsubscribe all the tasks of the scheduler and ends it
   *
   * @param scheduleId
   */
  public finish(scheduleId: string): void {
    Array.from(this.subscription$.values())
      .filter((data: SchedulerSubscription) => data.schedulerId === scheduleId)
      .forEach(sub$ => {
        sub$.subscription?.unsubscribe();
        this.subscription$.delete(sub$.subscriptionId);
      });

    this.schedulers.get(scheduleId)?.unregister();
    this.schedulers.delete(scheduleId);

    _console.log(`Scheduler ${scheduleId} finisehd...`, this.subscription$);
  }

  /**
   * Subscribe a specified task and return a unique subscription id
   *
   * @param schedulerId
   * @param fn
   * @returns subscription Id
   */
  public subscribe<D>(schedulerId: string, fn: (data?: D) => void): string | undefined {
    const scheduler = this.schedulers.get(schedulerId);

    if (scheduler) {
      const subscriptionId = `${UniqueIds.next()}`;
      this.subscription$.set(subscriptionId, { schedulerId, subscriptionId, subscription: scheduler.register().subscribe(fn) });

      _console.log(`Subscribe ${subscriptionId} task...`, this);

      return subscriptionId;
    }

    return undefined;
  }

  /**
   * Unsubscribe from a specified task
   *
   * @param subscriptionId
   */
  public unsubscribe(subscriptionId: string): void {
    const sub$ = this.subscription$.get(subscriptionId);

    if (sub$) {
      sub$.subscription?.unsubscribe();
      const scheduler = this.schedulers.get(sub$.schedulerId);
      scheduler?.unregister();
    }

    this.subscription$.delete(subscriptionId);

    _console.log(`Unsubscribe ${subscriptionId} task...`, this);
  }

  /**
   *
   * @param schedulerId
   * @returns
   */
  public exists(schedulerId: string): boolean {
    return this.schedulers.has(schedulerId);
  }
}
