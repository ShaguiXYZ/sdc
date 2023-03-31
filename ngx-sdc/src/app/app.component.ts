import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { UiContextDataService, UiSessionService } from './core/services';
import { ContextDataInfo } from './shared/constants/context-data';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [UiSessionService]
})
export class AppComponent implements OnInit, OnDestroy {
  public sessionLoaded = false;
  private language$!: Subscription;

  constructor(private contextData: UiContextDataService, private sessionService: UiSessionService) {}

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

  ngOnInit(): void {
    this.sessionService.sdcSession().then(session => {
      this.contextData.setContextData(ContextDataInfo.SDC_SESSION_DATA, session, { persistent: true });
      this.sessionLoaded = true;
    });
  }

  ngOnDestroy(): void {
    this.language$.unsubscribe();
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
