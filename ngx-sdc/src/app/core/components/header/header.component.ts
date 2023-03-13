import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { filter, Subscription } from 'rxjs';
import { IUserModel } from 'src/app/core/models/security';
import { UiSecurityInfo } from 'src/app/core/models/security/security.model';
import { UiAppContextData } from 'src/app/core/services/context-data.service';
import { UiLanguageService } from 'src/app/core/services/language.service';
import { UiSecurityService } from 'src/app/core/services/security.service';
import { Languages } from 'src/app/shared/config/app.constants';
import { ContextDataNames } from 'src/app/shared/config/contextInfo';
import { ButtonConfig } from 'src/app/shared/models';
import { IHeaderConfig, NX_HEADER_CONFIG } from './header.model';
import INavigation, { DEFAULT_HEADER_MENU, INavHeaderItem } from './navigation.model';

/**
 * Component for a header with two levels menu.
 *
 * One menu row is always visible and every item can show the child menu row if any when the mouse enters the parent item.
 * All the ids in NavParentItem and NavChildItem must be different among them.
 */
const imagesIconMenu = {
  open: 'chevron-down',
  close: 'chevron-up'
};

@Component({
  selector: 'ui-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class UiHeaderComponent implements OnInit, OnDestroy {
  public navigation!: INavigation;
  public languageButtons: ButtonConfig[] = [];
  public currentLanguage!: Languages;
  public isUserLogged = false;

  public securityInfo: {
    currentUser?: IUserModel;
    isItUser?: boolean;
  } = {};

  private security$!: Subscription;
  private language$!: Subscription;

  constructor(
    @Inject(NX_HEADER_CONFIG) private config: IHeaderConfig = { navigation: DEFAULT_HEADER_MENU },
    private appContextData: UiAppContextData,
    private languageService: UiLanguageService,
    private securityService: UiSecurityService
  ) {}

  ngOnInit() {
    this.initNavigation();
    this.initLanguages();

    this.updateSecurityData();

    this.security$ = this.appContextData
      .onDataChange()
      .pipe(filter(key => key === ContextDataNames.securityInfo))
      .subscribe(() => {
        this.updateSecurityData();
      });
  }

  ngOnDestroy() {
    this.security$.unsubscribe();
    this.language$.unsubscribe();
  }

  public getImatgeNavParent(collapsed?: boolean): string {
    return collapsed === true ? imagesIconMenu.open : imagesIconMenu.close;
  }

  public mouseEnterEvent(id: string): void {
    if (this.navigation) {
      this.navigation.routes.forEach((navParent: INavHeaderItem) => {
        navParent.collapsed = !(navParent.id === id && navParent.children?.length);
      });

      this.navigation.activeParent = id;
    }
  }

  public mouseLeaveEvent(id: string): void {
    if (this.navigation) {
      this.navigation.routes.forEach((navParent: INavHeaderItem) => {
        // exit from upper level of same headerParent
        if (navParent.id === id && navParent.children?.length && id === this.navigation.activeParent) {
          navParent.collapsed = false;
        } else {
          navParent.collapsed = true;
          this.navigation.activeParent = undefined;
        }
      });
    }
  }

  public signout() {
    this.securityService.logout();
  }

  private updateSecurityData = () => {
    this.isUserLogged = UiSecurityInfo.isLogged(this.appContextData.securityInfo);

    this.securityInfo = {
      currentUser: UiSecurityInfo.getUser(this.appContextData.securityInfo),
      isItUser: this.securityService.isItUser()
    };
  };

  private initNavigation(): void {
    this.navigation = this.config.navigation;
  }

  private initLanguages() {
    this.currentLanguage = this.appContextData.appConfig.lang;
    this.languageOptions(this.currentLanguage);

    this.language$ = this.appContextData
      .onDataChange()
      .pipe(filter(key => key === ContextDataNames.appConfig))
      .subscribe(() => {
        this.currentLanguage = this.appContextData.appConfig.lang;
        this.languageOptions(this.currentLanguage);
      });
  }

  private languageOptions(currentLanguage: Languages) {
    // this.languageButtons = [];
    // const languageKeys = Languages.keys();
    // languageKeys
    //     .filter(lang => Languages[lang] !== currentLanguage)
    //     .forEach(key => {
    //         const languageButton = new ButtonConfig(`language.${Languages[key]}`);
    //         languageButton.options = {
    //             language: Languages[key]
    //         };
    //         languageButton.callback = (options: any) => this.languageService.i18n(options.language);
    //         this.languageButtons = this.languageButtons.concat(languageButton);
    //     });
  }
}
