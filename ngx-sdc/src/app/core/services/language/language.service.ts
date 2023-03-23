import { EventEmitter, Inject, Injectable, Optional } from '@angular/core';
import { DEFAULT_LANGUAGE, TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { Languages } from './models';
import { LanguageConfig, NX_LANGUAGE_CONFIG, SESSION_LANGUAGE_KEY } from './models/language.model';

@Injectable({
  providedIn: 'root'
})
export class UiLanguageService {
  private languageChange$: EventEmitter<string> = new EventEmitter();

  constructor(@Optional() @Inject(NX_LANGUAGE_CONFIG) public languageConfig: LanguageConfig, private translateService: TranslateService) {
    this.configureService();
  }

  public i18n(key: string): void {
    const language = this.languageConfig.languages[key];

    if (language) {
      this.languageConfig.value = key;
      this.translateService.setDefaultLang(language);

      sessionStorage.setItem(SESSION_LANGUAGE_KEY, JSON.stringify(this.languageConfig));

      this.languageChange$.emit(key);
    }
  }

  public getLang(): string {
    return this.languageConfig.value;
  }

  public getLanguages(): { [key: string]: string } {
    return this.languageConfig.languages;
  }

  public asObservable(): Observable<string> {
    return this.languageChange$.asObservable();
  }

  private configureService(): void {
    const sessionData = sessionStorage.getItem(SESSION_LANGUAGE_KEY);

    if (sessionData) {
      this.languageConfig = JSON.parse(sessionData);
    } else {
      this.languageConfig = this.languageConfig || { value: DEFAULT_LANGUAGE, languages: Languages };
    }

    this.i18n(this.languageConfig.value);
  }
}
