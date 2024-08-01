import { DOCUMENT } from '@angular/common';
import { Inject, Injectable, LOCALE_ID, Optional } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Observable, Subject } from 'rxjs';
import { Languages, NX_LANGUAGE_CONFIG, SESSION_LANGUAGE_KEY } from './constants';
import { LanguageConfig } from './models';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {
  private languageChange$ = new Subject<string>();

  constructor(
    @Optional() @Inject(NX_LANGUAGE_CONFIG) private languageConfig: LanguageConfig,
    @Inject(DOCUMENT) private readonly document: Document,
    @Inject(LOCALE_ID) private readonly locale: string,
    private readonly translateService: TranslateService
  ) {
    this.configureService();
    this.i18n(this.getLangKey() || this.locale);
  }

  public i18n(key: string): void {
    const value = this.languageConfig.languages[key];

    if (value) {
      this.languageConfig.value = key;
      this.translateService.setDefaultLang(value);
      this.document.documentElement.lang = value;

      this.languageChange$.next(key);
    }

    localStorage.setItem(SESSION_LANGUAGE_KEY, JSON.stringify(this.languageConfig));
  }

  public getLang(): string {
    return this.languageConfig.languages[this.languageConfig.value];
  }

  public getLangKey(): string {
    return this.languageConfig.value;
  }

  public getLanguages(): { [key: string]: string } {
    return this.languageConfig.languages;
  }

  public asObservable(): Observable<string> {
    return this.languageChange$.asObservable();
  }

  private configureService(): void {
    const sessionData = localStorage.getItem(SESSION_LANGUAGE_KEY);

    if (sessionData) {
      this.languageConfig = JSON.parse(sessionData);
    } else {
      const key = Object.keys(Languages)[0];
      this.languageConfig = { ...{ value: key, languages: Languages }, ...this.languageConfig };
    }

    this.i18n(this.languageConfig.value);
  }
}
