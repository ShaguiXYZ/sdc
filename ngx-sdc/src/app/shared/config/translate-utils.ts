import { MissingTranslationHandler, MissingTranslationHandlerParams, TranslateModuleConfig } from '@ngx-translate/core';
import { provideTranslateHttpLoader } from '@ngx-translate/http-loader';

class CustomMissingTranslationHandler implements MissingTranslationHandler {
  public handle(params: MissingTranslationHandlerParams): string {
    return `!${params.key}!`;
  }
}

export const TRANSLATE_MODULE_CONFIG: TranslateModuleConfig = {
  loader: provideTranslateHttpLoader({ prefix: './assets/i18n/', suffix: '.json' }),
  missingTranslationHandler: { provide: MissingTranslationHandler, useClass: CustomMissingTranslationHandler }
};
