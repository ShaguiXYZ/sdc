import { Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject, Subscription } from 'rxjs';
import { ButtonConfig } from 'src/app/core/models';
import { UiLanguageService } from 'src/app/core/services';
import { ILanguageHeader } from '../models';

@Injectable()
export class HeaderLanguageService implements OnDestroy {
  private _languageInfo: ILanguageHeader = { languageButtons: [] };
  private languageChange$: Subject<ILanguageHeader>;
  private language$: Subscription;

  constructor(private languageService: UiLanguageService) {
    this.languageChange$ = new Subject<ILanguageHeader>();

    this._languageInfo.currentLanguage = this.languageService.getLang();
    this.languageOptions();

    this.language$ = this.languageService.asObservable().subscribe(key => {
      this._languageInfo.currentLanguage = key;
      this.languageOptions();
    });
  }

  public ngOnDestroy() {
    this.language$.unsubscribe();
  }

  get info(): ILanguageHeader {
    return this._languageInfo;
  }

  public onLanguageChange(): Observable<ILanguageHeader> {
    return this.languageChange$.asObservable();
  }

  private languageOptions() {
    this._languageInfo.languageButtons = [];
    const languageKeys = Object.keys(this.languageService.getLanguages());

    languageKeys
      .filter(lang => lang !== this._languageInfo.currentLanguage)
      .forEach(key => {
        const languageButton = new ButtonConfig(`Language.${key}`);

        languageButton.options = {
          language: key
        };

        languageButton.callback = (options: any) => this.languageService.i18n(options.language);
        this._languageInfo.languageButtons = this._languageInfo.languageButtons.concat(languageButton);
      });

    this.languageChange$.next(this._languageInfo);
  }
}