import { environment } from 'src/environments/environment';

export * from './keys';
export * from './metric-state-utils';
export * from './object-utils';

// eslint-disable-next-line @typescript-eslint/no-empty-function
export const emptyFn = (): void => {};

export const logError = (reason: any): void => console.error(reason);

export const _console = {
  log: (message?: any, ...optionalParams: any[]) => {
    if (Object.keys(environment).includes('debugMode')) {
      console.log(message, optionalParams);
    }
  }
};
