import { InjectionToken } from '@angular/core';
import INavigation from './navigation.model';

export interface IHeaderConfig {
  navigation: INavigation;
}

export const NX_HEADER_CONFIG = new InjectionToken<IHeaderConfig>(
  'NX_HEADER_CONFIG'
);
