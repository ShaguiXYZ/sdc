import { Component, HostListener, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { RouterOutlet } from '@angular/router';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { TranslateModule } from '@ngx-translate/core';
import { filter } from 'rxjs';
import { AlertComponent, HeaderComponent, LoadingComponent, NotificationComponent } from './core/components';
import { ContextDataService, StorageService } from './core/services';
import { AppConfigurationService } from './core/services/sdc/app-configuration.service';
import { routingAnimation } from './shared/animations';
import { SdcSseEventComponent } from './shared/components';
import { ContextDataInfo } from './shared/constants';

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
          display: flex;
          flex: 1;
          flex-direction: column;
          overflow: hidden scroll;
          align-items: center;

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

          footer {
            align-items: center;
            bottom: 0;
            display: flex;
            height: 40px;
            justify-content: flex-end;
            overflow: hidden;
            pointer-events: none;
            position: absolute;
            text-align: center;

            .sdc-footer-actions {
              .sdc-sse-event {
                height: 15px;
                pointer-events: all;
                width: 15px;
              }
            }
          }
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
        <footer nxLayout="grid maxwidth nogutters">
          <div class="sdc-footer-actions">
            <div class="sdc-sse-event">
              <sdc-sse-event></sdc-sse-event>
            </div>
          </div>
        </footer>
      </div>
    </main>
  `,
  animations: [routingAnimation],
  standalone: true,
  imports: [
    AlertComponent,
    HeaderComponent,
    LoadingComponent,
    NotificationComponent,
    NxGridModule,
    RouterOutlet,
    SdcSseEventComponent,
    TranslateModule
  ]
})
export class AppComponent implements OnInit {
  constructor(
    private readonly appConfiguration: AppConfigurationService,
    private readonly contextDataService: ContextDataService,
    private readonly storageService: StorageService,
    private readonly title: Title
  ) {}

  ngOnInit(): void {
    this.appConfiguration.appConfiguracions().then(config => {
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
