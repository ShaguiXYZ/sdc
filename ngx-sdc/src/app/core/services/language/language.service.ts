import { DOCUMENT } from '@angular/common';
import { EventEmitter, Inject, Injectable, LOCALE_ID, Optional } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { Languages } from './models';
import { LanguageConfig, NX_LANGUAGE_CONFIG, SESSION_LANGUAGE_KEY } from './models/language.model';

@Injectable({
  providedIn: 'root'
})
export class UiLanguageService {
  private languageChange$: EventEmitter<string> = new EventEmitter();

  constructor(
    @Optional() @Inject(NX_LANGUAGE_CONFIG) public languageConfig: LanguageConfig,
    @Inject(DOCUMENT) private document: Document,
    @Inject(LOCALE_ID) private locale: string,
    private translateService: TranslateService
  ) {

    this.configureService();

    this.document.documentElement.lang = this.getLang() || this.locale;
  }

  public i18n(key: string): void {
    const value = this.languageConfig.languages[key];

    if (value) {
      this.languageConfig.value = key;
      this.translateService.setDefaultLang(value);
      this.document.documentElement.lang = value;

      this.languageChange$.emit(key);
    }

    sessionStorage.setItem(SESSION_LANGUAGE_KEY, JSON.stringify(this.languageConfig));
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
      const key = Object.keys(Languages)[0];
      this.languageConfig = { ...{ value: key, languages: Languages }, ...this.languageConfig };
    }

    this.i18n(this.languageConfig.value);
  }
}