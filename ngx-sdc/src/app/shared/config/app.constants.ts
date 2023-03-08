/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
import packageJson from '../../../../package.json';

export const APP_NAME = packageJson.name;
export const APP_VERSION = packageJson.version;

export const DEFAULT_TIMEOUT_NOTIFICATIONS = 6000;

export enum Languages {
  enGB = 'en-GB',
  esCO = 'es-CO',
  esES = 'es-ES',
  esBR = 'pt-BR',
  esPT = 'pt-PT'
}

export namespace Languages {
  export const keys = (): string[] => {
    const optionsLanguages = Object.keys(Languages);
    optionsLanguages.splice(optionsLanguages.length - 1, 1); // Removes keys function
    return optionsLanguages;
  };
}

export class HttpStatus {
  public static readonly success = 200;
  public static readonly created = 201;
  public static readonly noContent = 204;
  public static readonly badRequest = 400;
  public static readonly notFound = 404;
  public static readonly conflict = 409;
  public static readonly internalServerError = 500;
}
