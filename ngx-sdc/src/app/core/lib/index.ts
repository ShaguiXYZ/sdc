export * from './keys';
export * from './metric-state-utils';
export * from './object-utils';

// eslint-disable-next-line @typescript-eslint/no-empty-function
export const emptyFn = (): void => {};

export const logError = (reason: any): void => console.error(reason);
