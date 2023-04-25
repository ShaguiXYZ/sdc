import { APP_NAME } from 'src/app/core/constants/app.constants';

export * from './context-data';
export * from './contex.model';

const COOKIE_NAME = `${APP_NAME.toLocaleUpperCase()}__DATA`;
export const storageKey = (key?: string, prefix: string = COOKIE_NAME) => (key ? `${prefix}__${key}` : COOKIE_NAME);
