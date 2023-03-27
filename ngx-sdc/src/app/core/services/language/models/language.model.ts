import { InjectionToken } from '@angular/core';
import { APP_NAME } from 'src/app/core/constants/app.constants';

export const SESSION_LANGUAGE_KEY = `${APP_NAME.toUpperCase()}_LANGUAGE_CONFIG`;

export const Languages = {
  ['enGB']: 'en-GB',
  ['esCO']: 'es-CO',
  ['esES']: 'es-ES',
  ['ptBR']: 'pt-BR',
  ['ptPT']: 'pt-PT'
};

export interface LanguageConfig {
  value: string;
  languages: { [key: string]: string };
}

export const NX_LANGUAGE_CONFIG = new InjectionToken<LanguageConfig>('NX_LANGUAGE_CONFIG');
