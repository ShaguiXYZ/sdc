import { Component, HostListener, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { RouterOutlet } from '@angular/router';
import { filter } from 'rxjs';
import { ContextDataService, StorageService } from './core/services';
import { routingAnimation } from './shared/animations';
import { ContextDataInfo } from './shared/constants';
import { AppConfig } from './shared/models';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { AlertComponent, HeaderComponent, LoadingComponent, NotificationComponent } from './core/components';

@Component({
  selector: 'app-root',
  template: `
    <main [@routeAnimations]="prepareRoute(outlet)">
      <nx-loading></nx-loading>
      <nx-alert></nx-alert>
      <nx-notification></nx-notification>

      <nx-header></nx-header>
      <div class="app-content sdc-scrollable-body">
        <div nxLayout="grid maxwidth nogutters">
          <router-outlet #outlet="outlet"></router-outlet>
        </div>
      </div>
      <footer>My footer</footer>
    </main>
  `,
  animations: [routingAnimation],
  standalone: true,
  imports: [AlertComponent, HeaderComponent, LoadingComponent, NotificationComponent, NxGridModule, RouterOutlet]
})
export class AppComponent implements OnInit {
  constructor(
    private readonly contextDataService: ContextDataService,
    private readonly storageService: StorageService,
    private readonly title: Title
  ) {}

  ngOnInit(): void {
    this.contextDataService.set(ContextDataInfo.APP_CONFIG, { title: '- S D C -' }, { persistent: true, referenced: false });

    this.contextDataService
      .onDataChange()
      .pipe(filter(data => data === ContextDataInfo.APP_CONFIG))
      .subscribe(data => {
        const config = this.contextDataService.get(data) as AppConfig;
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
