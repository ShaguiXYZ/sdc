import { environment } from 'src/environments/environment';

export * from './keys';
export * from './metric-state-utils';
export * from './object-utils';

export const emptyFn = (): void => {
  /* Empty fn */
};

export const _console = {
  log: (message?: any, ...optionalParams: any[]) => {
    if (Object.keys(environment).includes('debugMode')) {
      console.log(message, optionalParams);
    }
  },
  error: (reason: any, ...optionalParams: any[]): void => console.error(reason, optionalParams)
};
