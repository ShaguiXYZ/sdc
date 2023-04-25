import { Observable, Subject, Subscription, interval } from 'rxjs';

const DEFAULT_PERIOD = 15000;

export class UiTaskScheduler<D> {
  private task$: Subject<D>;
  private scheduler$?: Subscription;

  constructor(private task: () => D, private period: number = DEFAULT_PERIOD) {
    this.task$ = new Subject<D>();
  }

  public get isObserved(): boolean {
    return this.task$?.observed;
  }

  public register(): Observable<D> {
    if (!this.isObserved) {
      this.runScheduler();
    }

    return this.task$.asObservable();
  }

  public unregister() {
    if (!this.isObserved) {
      console.log('Stop scheduler...');

      this.scheduler$?.unsubscribe();
    }
  }

  /**
   * Run the task periodically
   */
  private runScheduler() {
    this.scheduler$ = interval(this.period).subscribe(() => {
      console.log('Run scheduler observers...', this.isObserved);

      this.task$.next(this.task());
    });
  }
}
