import { Component, Inject, OnDestroy, OnInit, Optional } from '@angular/core';
import { Subscription } from 'rxjs';
import {
  DEFAULT_HEADER_MENU,
  IHeaderConfig,
  ILanguageHeader,
  INavHeaderItem,
  INavigation,
  ISecurityHeader,
  NX_HEADER_CONFIG
} from './models';
import { HeaderLanguageService, HeaderSecurityService } from './services';

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
  selector: 'nx-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  providers: [HeaderLanguageService, HeaderSecurityService]
})
export class UiHeaderComponent implements OnInit, OnDestroy {
  public navigation!: INavigation;
  public securityInfo!: ISecurityHeader;
  public languageInfo!: ILanguageHeader;

  private language$!: Subscription;

  constructor(
    @Optional() @Inject(NX_HEADER_CONFIG) private config: IHeaderConfig,
    private languageService: HeaderLanguageService,
    private securityService: HeaderSecurityService
  ) {}

  ngOnInit() {
    if (!this.config) {
      this.config = { navigation: DEFAULT_HEADER_MENU };
    }

    this.initNavigation();

    this.securityInfo = this.securityService.info;
    this.languageInfo = this.languageService.info;

    this.language$ = this.languageService.onLanguageChange().subscribe(info => (this.languageInfo = info));
  }

  ngOnDestroy() {
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
    this.securityService.signout();
  }

  private initNavigation(): void {
    this.navigation = this.config.navigation;
  }
}
