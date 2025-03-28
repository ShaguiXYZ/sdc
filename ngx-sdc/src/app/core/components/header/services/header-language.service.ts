import { Injectable, OnDestroy } from '@angular/core';
import { ButtonConfig } from '@shagui/ng-shagui/core';
import { Observable, Subject, Subscription } from 'rxjs';
import { LanguageService } from 'src/app/core/services';
import { ILanguageHeader, LanguageButtonOption } from '../models';

@Injectable()
export class HeaderLanguageService implements OnDestroy {
  private _languageInfo: ILanguageHeader = { languageButtons: [] };
  private languageChange$: Subject<ILanguageHeader>;
  private language$: Subscription;

  constructor(private languageService: LanguageService) {
    this.languageChange$ = new Subject<ILanguageHeader>();

    this._languageInfo.currentLanguage = this.languageService.getLangKey();
    this.languageOptions();

    this.language$ = this.languageService.asObservable().subscribe(key => (this._languageInfo.currentLanguage = key));
  }

  get info(): ILanguageHeader {
    return this._languageInfo;
  }

  public ngOnDestroy() {
    this.language$.unsubscribe();
  }

  public onLanguageChange(): Observable<ILanguageHeader> {
    return this.languageChange$.asObservable();
  }

  private languageOptions() {
    this._languageInfo.languageButtons = [];
    const languageKeys = Object.keys(this.languageService.getLanguages());

    languageKeys.forEach(language => {
      const languageButton = new ButtonConfig(`Language.${language}`);

      languageButton.options = {
        language
      };

      languageButton.callback = (options: LanguageButtonOption) => this.languageService.i18n(options.language);
      languageButton.isVisible = () => language !== this._languageInfo.currentLanguage;
      this._languageInfo.languageButtons = this._languageInfo.languageButtons.concat(languageButton);
    });

    this.languageChange$.next(this._languageInfo);
  }
}
