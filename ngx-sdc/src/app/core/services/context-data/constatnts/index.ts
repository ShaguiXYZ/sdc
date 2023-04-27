import { InjectionToken } from '@angular/core';
import { APP_NAME } from 'src/app/core/constants';
import { ContextConfig } from '../models';

export const NX_CONTEX_CONFIG = new InjectionToken<ContextConfig>('NX_CONTEX_CONFIG');

export const contextStorageID = `CTX_${APP_NAME.toUpperCase()}`; // Key for data how is saved in session

export const MIN_CACHE_SCHEDULER_PERIOD = 60 * 1000;
