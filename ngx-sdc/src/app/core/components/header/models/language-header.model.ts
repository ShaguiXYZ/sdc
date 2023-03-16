import { Languages } from 'src/app/shared/config/languages';
import { ButtonConfig } from 'src/app/shared/models';

export interface ILanguageHeader {
  languageButtons: ButtonConfig[];
  currentLanguage?: Languages;
}
