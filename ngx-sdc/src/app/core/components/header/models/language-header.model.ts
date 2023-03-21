import { ButtonConfig } from 'src/app/core/models';
import { Languages } from 'src/app/core/constants/languages';

export interface ILanguageHeader {
  languageButtons: ButtonConfig[];
  currentLanguage?: Languages;
}
