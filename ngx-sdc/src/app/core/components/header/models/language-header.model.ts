import { ButtonConfig } from 'src/app/core/models';
import { Languages } from 'src/app/shared/config/languages';

export interface ILanguageHeader {
  languageButtons: ButtonConfig[];
  currentLanguage?: Languages;
}
