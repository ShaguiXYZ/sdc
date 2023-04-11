import { Component, HostListener, OnInit } from '@angular/core';
import { ContextDataInfo } from './shared/constants/context-data';
import { UiContextDataService } from './core/services';
import { UiCookieService } from './core/services/context-data/cookie.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  constructor(private cookieService: UiCookieService) {}

  ngOnInit(): void {
    this.cookieService.retrieve(ContextDataInfo.SUMMARY_DATA);
  }

  // Detect the Closing of a Browser Tab
  @HostListener('window:beforeunload', ['$event'])
  beforeunloadHandler(event: { preventDefault: () => void; returnValue: string }) {
    event.preventDefault();
    this.cookieService.create(ContextDataInfo.SUMMARY_DATA);
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
