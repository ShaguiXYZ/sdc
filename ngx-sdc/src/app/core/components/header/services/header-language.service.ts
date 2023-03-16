import { Injectable, OnDestroy } from '@angular/core';
import { filter, Observable, Subject, Subscription } from 'rxjs';
import { UiLanguageService } from 'src/app/core/services/language.service';
import { ContextDataNames } from 'src/app/shared/config/contextInfo';
import { Languages } from 'src/app/shared/config/languages';
import { ButtonConfig } from 'src/app/shared/models';
import { UiAppContextData } from '../../../services';
import { ILanguageHeader } from '../models';

@Injectable()
export class HeaderLanguageService implements OnDestroy {
  private _info: ILanguageHeader = { languageButtons: [] };
  private languageChange$: Subject<ILanguageHeader>;
  private language$: Subscription;

  constructor(private appContextData: UiAppContextData, private languageService: UiLanguageService) {
    this.languageChange$ = new Subject<ILanguageHeader>();

    this._info.currentLanguage = this.appContextData.appConfig?.lang;
    this.languageOptions(this._info.currentLanguage);

    this.language$ = this.appContextData
      .onDataChange()
      .pipe(filter(key => key === ContextDataNames.appConfig))
      .subscribe(() => {
        this._info.currentLanguage = this.appContextData.appConfig.lang;
        this.languageOptions(this._info.currentLanguage);
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
