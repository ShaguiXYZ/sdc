import { DOCUMENT } from '@angular/common';
import { Component, Inject, LOCALE_ID, OnDestroy, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { AppConfig } from './core/services/context-data/models/app-config.model';
import { UiAppContextDataService, UiLanguageService, UiSessionService } from './core/services';
import { ContextDataNames } from './shared/config/context-info';
import { CoreContextDataNames } from './core/services/context-data';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [UiSessionService]
})
export class AppComponent implements OnInit, OnDestroy {
  public sessionLoaded = false;
  private language$!: Subscription;

  constructor(
    @Inject(DOCUMENT) private document: Document,
    @Inject(LOCALE_ID) private locale: string,
    private contextData: UiAppContextDataService,
    private languageService: UiLanguageService,
    private translate: TranslateService,
    private sessionService: UiSessionService
  ) {}

  ngOnInit(): void {
    if (!this.contextData.getContextData(CoreContextDataNames.appConfig)) {
      this.contextData.setContextData(CoreContextDataNames.appConfig, new AppConfig());
    }

    const appConfig = this.contextData.getContextData(CoreContextDataNames.appConfig) as AppConfig;

    this.sessionService.sdcSession().then(session => {
      this.contextData.setContextData(ContextDataNames.sdcSessionData, session);
      this.sessionLoaded = true;
    });

    this.translate.setDefaultLang(appConfig.lang);
    this.document.documentElement.lang = this.translate.getDefaultLang() || this.locale || appConfig.lang;

    this.language$ = this.languageService.asObservable().subscribe((lang: string) => {
      this.translate.setDefaultLang(lang);
      this.document.documentElement.lang = lang;
    });
  }

  ngOnDestroy(): void {
    this.language$.unsubscribe();
  }
}
