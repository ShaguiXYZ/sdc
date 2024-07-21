import { CommonModule } from '@angular/common';
import { Component, Inject, Input, OnDestroy, OnInit, Optional } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxContextMenuModule } from '@aposin/ng-aquila/context-menu';
import { NxHeaderModule } from '@aposin/ng-aquila/header';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { NxTooltipModule } from '@aposin/ng-aquila/tooltip';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { SwitchThemeComponent } from './components';
import { DEFAULT_HEADER_MENU, IHeaderConfig, ILanguageHeader, INavHeaderItem, INavigation, NX_HEADER_CONFIG } from './models';
import { HeaderLanguageService } from './services';

/**
 * Component for a header with two levels menu.
 *
 * One menu row is always visible and every item can show the child menu row if any when the mouse enters the parent item.
 * All the ids in NavParentItem and NavChildItem must be different among them.
 */
@Component({
  selector: 'nx-header',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header.component.html',
  providers: [HeaderLanguageService],
  standalone: true,
  imports: [
    CommonModule,
    NxButtonModule,
    NxContextMenuModule,
    NxHeaderModule,
    NxLinkModule,
    NxTooltipModule,
    SwitchThemeComponent,
    RouterModule,
    TranslateModule
  ]
})
export class HeaderComponent implements OnInit, OnDestroy {
  @Input()
  public headerTitle?: string;

  @Input()
  public title = '';

  public navigation!: INavigation;
  public languageInfo!: ILanguageHeader;
  public logo?: string;
  public themeSwitcher = false;

  private language$!: Subscription;

  constructor(
    @Optional() @Inject(NX_HEADER_CONFIG) private config: IHeaderConfig,
    private readonly languageService: HeaderLanguageService
  ) {}

  ngOnInit() {
    this.navigation = this.config?.navigation ?? DEFAULT_HEADER_MENU;
    this.languageInfo = this.languageService.info;
    this.themeSwitcher = this.config?.themeSwitcher ?? false;
    this.logo = this.config?.logo;

    this.language$ = this.languageService.onLanguageChange().subscribe(info => (this.languageInfo = info));
  }

  ngOnDestroy() {
    this.language$.unsubscribe();
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
}
