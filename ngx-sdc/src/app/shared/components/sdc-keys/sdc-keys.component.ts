import { Component, HostListener } from '@angular/core';
import { ScreenRecorder } from '@shagui/ng-shagui/core';
import { SdcOverlayService } from '../sdc-overlay/services';
import { SdcKeyComponent } from './components';

@Component({
  selector: 'sdc-keys',
  styles: [
    `
      .sdc-keys-content {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;

        .sdc-keys {
          max-height: 0;
        }
      }
    `
  ],
  template: `
    <div class="sdc-keys-content" (click)="toggleGlobalSearch()">
      <div class="selected-key">
        <sdc-key key="K" label="Label.Ctrl.K"></sdc-key>
      </div>
    </div>
  `,
  standalone: true,
  imports: [SdcKeyComponent]
})
export class SdcKeysComponent {
  constructor(private readonly overlayService: SdcOverlayService) {}

  @HostListener('document:keydown', ['$event'])
  onKeyDown(event: KeyboardEvent) {
    this.eventKeyActions(event);

    // @howto: Detect platform
    if (navigator.userAgent.toLowerCase().includes('windows')) {
      this.handleWindowsKeyEvents(event);
    } else {
      this.handleMacKeyEvents(event);
    }
  }

  public toggleGlobalSearch() {
    this.overlayService.toggleGlobalSearch();
  }

  private handleMacKeyEvents(event: KeyboardEvent) {
    // MetaKey documentation
    // https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/metaKey

    event.metaKey && this.eventCtrlKeyActions(event);
  }

  private handleWindowsKeyEvents(event: KeyboardEvent) {
    event.ctrlKey && this.eventCtrlKeyActions(event);
  }

  private eventCtrlKeyActions(event: KeyboardEvent) {
    switch (event.key.toUpperCase()) {
      case 'K':
        this.toggleGlobalSearch();
        event.preventDefault();
        break;
      case 'R':
        ScreenRecorder.startRecording();
        event.preventDefault();
        break;
      case 'P':
      case 'S':
        event.preventDefault();
        break;
      default:
        break;
    }
  }

  private eventKeyActions(event: KeyboardEvent): void {
    switch (event.key?.toUpperCase()) {
      case 'ESCAPE':
        this.overlayService.defaultOverlayState();
        event.preventDefault();
        break;
      case 'F1':
        this.overlayService.toggleHelp();
        event.preventDefault();
        break;
      default:
        break;
    }
  }
}
