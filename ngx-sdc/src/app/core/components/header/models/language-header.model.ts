import { ButtonConfig } from 'src/app/core/models';

export interface ILanguageHeader {
  languageButtons: ButtonConfig[];
  currentLanguage?: string;
}
