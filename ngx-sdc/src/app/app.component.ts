import { Component, HostListener, inject, OnDestroy, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { RouterOutlet } from '@angular/router';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { TranslateModule } from '@ngx-translate/core';
import { ContextDataService, StorageService } from '@shagui/ng-shagui/core';
import { Subscription } from 'rxjs';
import { HeaderComponent } from './core/components';
import { IAppConfigurationModel } from './core/models/sdc';
import { routingAnimation } from './shared/animations';
import { SdcAppFooterComponent, SdcOverlayComponent } from './shared/components';
import { SdcOverlayService } from './shared/components/sdc-overlay/services';
import { ContextDataInfo, retrieveAppContextData, storageAppContextData } from './shared/constants';

@Component({
    selector: 'app-root',
    styleUrls: ['./app.component.scss'],
    template: `
    <main>
      <nx-overlay />
      <article class="app-content sdc-scrollable-body" (click)="onClick()">
        <header nxLayout="grid maxwidth nogutters">
          <nx-header [headerTitle]="'Header.Title' | translate" [title]="'Header.Description' | translate" />
        </header>
        <section nxLayout="grid maxwidth nogutters" [@routeAnimations]="prepareRoute(outlet)">
          <router-outlet #outlet="outlet" />
        </section>
      </article>
      <sdc-footer />
    </main>
  `,
    animations: [routingAnimation],
    imports: [HeaderComponent, NxGridModule, RouterOutlet, SdcAppFooterComponent, SdcOverlayComponent, TranslateModule]
})
export class AppComponent implements OnInit, OnDestroy {
  private subscriptions$: Subscription[] = [];

  private readonly contextDataService = inject(ContextDataService);
  private readonly storageService = inject(StorageService);

  constructor(
    private readonly overlayService: SdcOverlayService,
    private readonly title: Title
  ) {}

  // @howto Detect the Closing of a Browser Tab
  @HostListener('window:beforeunload', ['$event'])
  beforeunloadHandler(event: { preventDefault: () => void; returnValue: string }) {
    event.preventDefault();

    storageAppContextData(this.storageService);
  }

  @HostListener('window:popstate', ['$event'])
  onPopState(event: PopStateEvent) {
    event.stopPropagation();
  }

  ngOnInit(): void {
    this.subscriptions$.push(
      this.contextDataService
        .onDataChange<IAppConfigurationModel>(ContextDataInfo.APP_CONFIG)
        .subscribe(config => this.title.setTitle(config.title))
    );

    retrieveAppContextData(this.storageService);
  }

  ngOnDestroy(): void {
    this.subscriptions$.forEach(subscription => subscription.unsubscribe());
  }

  // @howto animation on route change
  public prepareRoute = (outlet: RouterOutlet) => outlet.isActivated && outlet.activatedRouteData?.['animation'];

  public onClick() {
    this.overlayService.defaultOverlayState();
  }
}
