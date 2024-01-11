import { InjectionToken } from '@angular/core';
import { CountDownSeed } from './count-down-seed.model';

export const DEFAULT_COUNT_TIMEOUT = 30;
export const DEFAULT_COUNT_DOWN_PERIOD = 1000;

export const NX_COUNT_DOWN_SEED = new InjectionToken<CountDownSeed>('NX_COUNTDOWN_SEED');
