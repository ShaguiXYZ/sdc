import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { RouterOutlet } from '@angular/router';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { AlertComponent, HeaderComponent, LoadingComponent, NotificationComponent } from './core/components';
import { IAppConfiguration } from './core/models/sdc';
import { ContextDataService, StorageService } from './core/services';
import { AppConfigurationService } from './core/services/sdc/app-configuration.service';
import { routingAnimation } from './shared/animations';
import { SdcAppFooterComponent, SdcAppOverlayComponent } from './shared/components';
import { ContextDataInfo } from './shared/constants';

@Component({
  selector: 'app-root',
  styleUrls: ['./app.component.scss'],
  template: `
    <main [@routeAnimations]="prepareRoute(outlet)">
      <nx-loading />
      <nx-alert />
      <nx-notification />
      <sdc-overlay />
      <div class="app-content sdc-scrollable-body">
        <header nxLayout="grid maxwidth nogutters">
          <nx-header headerTitle="S D C" [title]="'Header.Title' | translate" />
        </header>
        <div nxLayout="grid maxwidth nogutters">
          <router-outlet #outlet="outlet" />
        </div>
      </div>
      <sdc-footer />
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
    SdcAppFooterComponent,
    SdcAppOverlayComponent,
    TranslateModule
  ]
})
export class AppComponent implements OnInit, OnDestroy {
  private subscriptions$: Subscription[] = [];

  constructor(
    private readonly appConfiguration: AppConfigurationService,
    private readonly contextDataService: ContextDataService,
    private readonly storageService: StorageService,
    private readonly title: Title
  ) {}

  ngOnInit(): void {
    this.appConfiguration.appConfiguracions().then(config => {
      this.contextDataService.set<IAppConfiguration>(
        ContextDataInfo.APP_CONFIG,
        { ...config, title: '- S D C -' },
        { persistent: true, referenced: false }
      );
    });

    this.subscriptions$.push(
      this.contextDataService.onDataChange<IAppConfiguration>(ContextDataInfo.APP_CONFIG).subscribe(config => {
        this.title.setTitle(config.title);
      })
    );

    this.storageService.retrieve(ContextDataInfo.SQUADS_DATA);
    this.storageService.retrieve(ContextDataInfo.DEPARTMENTS_DATA);
  }

  ngOnDestroy(): void {
    this.subscriptions$.forEach(subscription => subscription.unsubscribe());
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
