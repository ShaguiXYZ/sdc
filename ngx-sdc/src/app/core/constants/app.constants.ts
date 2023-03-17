/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
import packageJson from '../../../../package.json';

export const APP_NAME = packageJson.name;
export const APP_VERSION = packageJson.version;
export const DEFAULT_TIMEOUT_NOTIFICATIONS = 6000;
export const COLOR_PREFIX = 'color--';

export class HttpStatus {
  public static readonly success = 200;
  public static readonly created = 201;
  public static readonly noContent = 204;
  public static readonly badRequest = 400;
  public static readonly forbidden = 403;
  public static readonly notFound = 404;
  public static readonly conflict = 409;
  public static readonly internalServerError = 500;
}
