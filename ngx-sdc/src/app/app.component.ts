import { Component, HostListener, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { RouterOutlet } from '@angular/router';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { filter } from 'rxjs';
import { AlertComponent, HeaderComponent, LoadingComponent, NotificationComponent } from './core/components';
import { ContextDataService, StorageService } from './core/services';
import { routingAnimation } from './shared/animations';
import { ContextDataInfo } from './shared/constants';
import { TranslateModule } from '@ngx-translate/core';
import { AppConfigurationService } from './core/services/sdc/app-configuration.service';

@Component({
  selector: 'app-root',
  styles: [
    `
      @import 'core-colors';
      @import 'core-globals';

      main {
        display: flex;
        flex-direction: column;
        height: 100vh;
        overflow: hidden;
        user-select: none;
        -webkit-user-select: none;
        width: 100vw;

        .app-content {
          flex: 1;
          overflow: hidden scroll;

          header {
            position: sticky;
            top: 0;
            z-index: $z-index-over;

            animation: enhance-header linear both;
            animation-timeline: scroll(nearest block);
            animation-range: 0 15%;

            @keyframes enhance-header {
              from {
                backdrop-filter: blur(0px);
                opacity: 1;
              }

              to {
                backdrop-filter: blur(10px);
                opacity: 0.8;
              }
            }
          }
        }

        footer {
          border-top: 1px solid $grey-light-color;
          padding: 5px 16px;
          text-align: center;
        }
      }
    `
  ],
  template: `
    <main [@routeAnimations]="prepareRoute(outlet)">
      <nx-loading></nx-loading>
      <nx-alert></nx-alert>
      <nx-notification></nx-notification>

      <div class="app-content sdc-scrollable-body">
        <header nxLayout="grid maxwidth nogutters">
          <nx-header headerTitle="S D C" [title]="'Header.Title' | translate"></nx-header>
        </header>
        <div nxLayout="grid maxwidth nogutters">
          <router-outlet #outlet="outlet"></router-outlet>
        </div>
      </div>
    </main>
  `,
  animations: [routingAnimation],
  standalone: true,
  imports: [AlertComponent, HeaderComponent, LoadingComponent, NotificationComponent, NxGridModule, RouterOutlet, TranslateModule]
})
export class AppComponent implements OnInit {
  constructor(
    private readonly appConfiguration: AppConfigurationService,
    private readonly contextDataService: ContextDataService,
    private readonly storageService: StorageService,
    private readonly title: Title
  ) {}

  ngOnInit(): void {
    this.appConfiguration.AppConfiguracions().then(config => {
      this.contextDataService.set(ContextDataInfo.APP_CONFIG, { ...config, title: '- S D C -' }, { persistent: true, referenced: false });
    });

    this.contextDataService
      .onDataChange()
      .pipe(filter(data => data === ContextDataInfo.APP_CONFIG))
      .subscribe(data => {
        const config = this.contextDataService.get(data);
        this.title.setTitle(config.title);
      });

    this.storageService.retrieve(ContextDataInfo.SQUADS_DATA);
    this.storageService.retrieve(ContextDataInfo.DEPARTMENTS_DATA);
  }

  // Detect the Closing of a Browser Tab
  @HostListener('window:beforeunload', ['$event'])
  beforeunloadHandler(event: { preventDefault: () => void; returnValue: string }) {
    event.preventDefault();
    this.storageService.create(ContextDataInfo.SQUADS_DATA);
    this.storageService.create(ContextDataInfo.DEPARTMENTS_DATA);
  }

  @HostListener('window:popstate', ['$event'])
  onPopState(event: PopStateEvent) {
    event.stopPropagation();
  }

  @HostListener('document:keydown', ['$event'])
  onKeyDown(event: KeyboardEvent) {
    // Detect platform
    if (navigator.userAgent.toLowerCase().includes('windows')) {
      this.handleWindowsKeyEvents(event);
    } else {
      this.handleMacKeyEvents(event);
    }
  }

  public prepareRoute(outlet: RouterOutlet) {
    if (outlet.isActivated) return outlet.activatedRouteData?.['animation'];
  }

  private handleMacKeyEvents(event: KeyboardEvent) {
    // MetaKey documentation
    // https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/metaKey

    // Action on Cmd + S
    event.metaKey && event.key === 's' && event.preventDefault();
  }

  private handleWindowsKeyEvents(event: KeyboardEvent) {
    // Action on Ctrl + S
    event.ctrlKey && event.key === 's' && event.preventDefault();
  }
}
