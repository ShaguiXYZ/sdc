import { Component, HostListener } from '@angular/core';
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
    <div class="sdc-keys-content">
      <!-- <div class="sdc-keys">
        <div class="sdc-key">
          <sdc-key key="K" label="Label.Search"></sdc-key>
        </div>
      </div> -->
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
    // @howto: Detect platform
    if (navigator.userAgent.toLowerCase().includes('windows')) {
      this.handleWindowsKeyEvents(event);
    } else {
      this.handleMacKeyEvents(event);
    }
  }

  private handleMacKeyEvents(event: KeyboardEvent) {
    // MetaKey documentation
    // https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/metaKey

    this.eventKeyActions(event);
    event.metaKey && this.eventCtrlKeyActions(event);
  }

  private handleWindowsKeyEvents(event: KeyboardEvent) {
    this.eventKeyActions(event);
    event.ctrlKey && this.eventCtrlKeyActions(event);
  }

  private eventCtrlKeyActions(event: KeyboardEvent) {
    switch (event.key.toUpperCase()) {
      case 'K':
        this.overlayService.toggleGlobalSearch();
        event.preventDefault();
        break;
      case 'S':
        event.preventDefault();
        break;
      default:
        break;
    }
  }

  private eventKeyActions(event: KeyboardEvent) {
    switch (event.key.toUpperCase()) {
      case 'ESCAPE':
        this.overlayService.toggleGlobalSearch('closed');
        event.preventDefault();
        break;
      default:
        break;
    }
  }
}
