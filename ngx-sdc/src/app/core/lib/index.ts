import { environment } from 'src/environments/environment';

export * from './keys.lib';
export * from './object-utils.lib';
export * from './sdc-data.lib';

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
