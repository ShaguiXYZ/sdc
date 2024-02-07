import { Inject, Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { CountDownSeed } from './models';
import { DEFAULT_COUNT_DOWN_PERIOD, NX_COUNT_DOWN_SEED } from './models/count-down.model';

@Injectable()
export class CountDownService {
  private intervalId?: NodeJS.Timeout;

  private onExpired$: Subject<void>;
  private onTick$: Subject<any>;
  private onWarn$: Subject<any>;

  constructor(@Inject(NX_COUNT_DOWN_SEED) private seed: CountDownSeed) {
    this.onExpired$ = new Subject<any>();
    this.onTick$ = new Subject<any>();
    this.onWarn$ = new Subject<any>();
  }

  public startCountdown = () => {
    this.stopCountdown();
    this.intervalId = globalThis.setInterval(this.tick, this.seed.period?.() ?? DEFAULT_COUNT_DOWN_PERIOD);
  };

  public stopCountdown = () => {
    this.resetCountdown();

    if (this.intervalId) {
      clearInterval(this.intervalId);
      this.intervalId = undefined;
    }
  };

  public resetCountdown = () => this.seed.reset();

  public onExpired(): Observable<any> {
    return this.onExpired$.asObservable();
  }

  public onWarn(): Observable<any> {
    return this.onWarn$.asObservable();
  }

  public onTick(): Observable<any> {
    return this.onTick$.asObservable();
  }

  private tick = () => {
    if (this.seed.hasNext()) {
      const next = this.seed.next();
      this.onTick$.next(next);
      this.seed.warn?.() && this.onWarn$.next(next);
    } else {
      this.onExpired$.next();
      this.stopCountdown();
    }
  };
}
