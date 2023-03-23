import { DOCUMENT } from '@angular/common';
import { Component, Inject, LOCALE_ID, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { UiAppContextDataService, UiLanguageService, UiSessionService } from './core/services';
import { ContextDataNames } from './shared/config/context-info';

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
    private sessionService: UiSessionService
  ) {}

  ngOnInit(): void {
    this.sessionService.sdcSession().then(session => {
      this.contextData.setContextData(ContextDataNames.sdcSessionData, session, { persistent: true });
      this.sessionLoaded = true;
    });

   this.document.documentElement.lang = this.languageService.getLang() || this.locale;

    this.language$ = this.languageService.asObservable().subscribe((lang: string) => {
      this.document.documentElement.lang = lang;
    });
  }

  ngOnDestroy(): void {
    this.language$.unsubscribe();
  }
}
