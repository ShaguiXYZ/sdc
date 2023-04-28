import { Component, HostListener, OnInit } from '@angular/core';
import { UiStorageService } from './core/services/context-data';
import { ContextDataInfo } from './shared/constants/context-data';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  constructor(private storageService: UiStorageService) {}

  ngOnInit(): void {
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
