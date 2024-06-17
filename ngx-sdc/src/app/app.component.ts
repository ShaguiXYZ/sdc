import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { RouterOutlet } from '@angular/router';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { TranslateModule } from '@ngx-translate/core';
import { ContextDataService, StorageService } from '@shagui/ng-shagui/core';
import { Subscription } from 'rxjs';
import { AlertComponent, HeaderComponent, LoadingComponent, NotificationComponent } from './core/components';
import { IAppConfigurationModel } from './core/models/sdc';
import { routingAnimation } from './shared/animations';
import { SdcAppFooterComponent, SdcOverlayComponent } from './shared/components';
import { SdcOverlayService } from './shared/components/sdc-overlay/services';
import { ContextDataInfo } from './shared/constants';

@Component({
  selector: 'app-root',
  styleUrls: ['./app.component.scss'],
  template: `
    <main>
      <nx-loading />
      <nx-alert />
      <nx-notification />
      <sdc-overlay />
      <div class="app-content sdc-scrollable-body" (click)="onClick($event)">
        <header nxLayout="grid maxwidth nogutters">
          <nx-header [headerTitle]="'Header.Title' | translate" [title]="'Header.Description' | translate" />
        </header>
        <div nxLayout="grid maxwidth nogutters" [@routeAnimations]="prepareRoute(outlet)">
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
    SdcOverlayComponent,
    TranslateModule
  ]
})
export class AppComponent implements OnInit, OnDestroy {
  private subscriptions$: Subscription[] = [];

  constructor(
    private readonly contextDataService: ContextDataService,
    private readonly overlayService: SdcOverlayService,
    private readonly storageService: StorageService,
    private readonly title: Title
  ) {}

  ngOnInit(): void {
    this.subscriptions$.push(
      this.contextDataService.onDataChange<IAppConfigurationModel>(ContextDataInfo.APP_CONFIG).subscribe(config => {
        this.title.setTitle(config.title);
      })
    );

    this.storageService.retrieve(ContextDataInfo.SQUADS_DATA);
    this.storageService.retrieve(ContextDataInfo.DEPARTMENTS_DATA);
  }

  ngOnDestroy(): void {
    this.subscriptions$.forEach(subscription => subscription.unsubscribe());
  }

  // @howto Detect the Closing of a Browser Tab
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

  // @howto animation on route change
  public prepareRoute = (outlet: RouterOutlet) => outlet.isActivated && outlet.activatedRouteData?.['animation'];

  public onClick(event: any) {
    this.overlayService.defaultOverlayState();
  }
}
