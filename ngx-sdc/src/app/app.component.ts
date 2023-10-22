import { Component, HostListener, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { RouterOutlet } from '@angular/router';
import { filter } from 'rxjs';
import { routingAnimation } from './app-routing-animations';
import { UiContextDataService, UiStorageService } from './core/services';
import { ContextDataInfo } from './shared/constants';
import { AppConfig } from './shared/models/app.-config.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  animations: [routingAnimation]
})
export class AppComponent implements OnInit {
  constructor(
    private readonly contextDataService: UiContextDataService,
    private readonly storageService: UiStorageService,
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

  prepareRoute(outlet: RouterOutlet) {
    if (outlet.isActivated) return outlet.activatedRouteData?.['animation'];
  }

  private handleMacKeyEvents(event: KeyboardEvent) {
    // MetaKey documentation
    // https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/metaKey

    if (event.metaKey && event.key === 's') {
      // Action on Cmd + S
      event.preventDefault();
    }
  }

  private handleWindowsKeyEvents(event: KeyboardEvent) {
    if (event.ctrlKey && event.key === 's') {
      // Action on Ctrl + S
      event.preventDefault();
    }
  }
}
