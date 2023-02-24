import { DOCUMENT } from '@angular/common';
import { Component, Inject, LOCALE_ID, OnDestroy, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { UiAppContextData } from './core/services/context-data.service';
import { UiLanguageService } from './core/services/language.service';
import { AppConfig, ContextDataNames } from './shared/config/contextInfo';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'ngx-sdc';

  private language$!: Subscription;

  constructor(
    @Inject(DOCUMENT) private document: Document,
    @Inject(LOCALE_ID) private locale: string,
    private contextData: UiAppContextData,
    private languageService: UiLanguageService,
    private translate: TranslateService
  ) {}

  ngOnInit(): void {
    if (!this.contextData.appConfig) {
      this.contextData.setContextData(ContextDataNames.appConfig, new AppConfig());
    }

    this.translate.setDefaultLang(this.contextData.appConfig.lang);
    this.document.documentElement.lang = this.translate.getDefaultLang() || this.locale || this.contextData.appConfig.lang;

    this.language$ = this.languageService.asObservable().subscribe((lang: string) => {
      this.translate.setDefaultLang(lang);
      this.document.documentElement.lang = lang;
    });
  }

  ngOnDestroy(): void {
    this.language$.unsubscribe();
  }
}
