import { InjectionToken } from '@angular/core';
import { INavigation } from '.';

export interface IHeaderConfig {
  logo?: string;
  navigation: INavigation;
  themeSwitcher?: boolean;
}

export const NX_HEADER_CONFIG = new InjectionToken<IHeaderConfig>('NX_HEADER_CONFIG');
