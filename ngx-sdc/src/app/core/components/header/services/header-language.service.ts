import { Injectable, OnDestroy } from '@angular/core';
import { filter, Observable, Subject, Subscription } from 'rxjs';
import { ButtonConfig } from 'src/app/core/models';
import { UiAppContextDataService, UiLanguageService } from 'src/app/core/services';
import { CoreContextDataNames } from 'src/app/core/services/context-data';
import { Languages } from 'src/app/core/services/language';
import { ILanguageHeader } from '../models';

@Injectable()
export class HeaderLanguageService implements OnDestroy {
  private _info: ILanguageHeader = { languageButtons: [] };
  private languageChange$: Subject<ILanguageHeader>;
  private language$: Subscription;

  constructor(private contextData: UiAppContextDataService, private languageService: UiLanguageService) {
    const appConfig = this.contextData.contextDataServiceConfiguration();
    this.languageChange$ = new Subject<ILanguageHeader>();

    this._info.currentLanguage = appConfig.lang;
    this.languageOptions(this._info.currentLanguage as Languages);

    this.language$ = this.contextData
      .onDataChange()
      .pipe(filter(key => key === CoreContextDataNames.appConfig))
      .subscribe(() => {
        this._info.currentLanguage = appConfig.lang;
        this.languageOptions(this._info.currentLanguage as Languages);
      });
  }

  public ngOnDestroy() {
    this.language$.unsubscribe();
  }

  get info(): ILanguageHeader {
    return this._info;
  }

  public onLanguageChange(): Observable<ILanguageHeader> {
    return this.languageChange$.asObservable();
  }

  private languageOptions(currentLanguage: Languages) {
    this._info.languageButtons = [];
    const languageKeys = Languages.keys();

    languageKeys
      .filter(lang => Languages.valueOf(lang) !== currentLanguage)
      .forEach(key => {
        const languageButton = new ButtonConfig(`Language.${Languages.valueOf(key)}`);
        languageButton.options = {
          language: Languages.valueOf(key)
        };
        languageButton.callback = (options: any) => this.languageService.i18n(options.language);
        this._info.languageButtons = this._info.languageButtons.concat(languageButton);
      });

    this.languageChange$.next(this._info);
  }
}
