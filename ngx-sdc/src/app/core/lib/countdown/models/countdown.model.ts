import { InjectionToken } from '@angular/core';
import { CountdownSeed } from './countdown-seed.model';

export const DEFAULT_COUNT_TIMEOUT = 30;
export const DEFAULT_COUNT_DOWN_PERIOD = 1000;

export const NX_COUNTDOWN_SEED = new InjectionToken<CountdownSeed>('NX_COUNTDOWN_SEED');
