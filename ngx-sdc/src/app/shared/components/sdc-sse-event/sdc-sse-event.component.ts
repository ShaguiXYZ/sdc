// create new component
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { NotificationService } from 'src/app/core/components';
import { SseEventModel, SseService } from 'src/app/core/services';

@Component({
  selector: 'sdc-sse-event',
  styles: [
    `
      @import 'core-globals';

      .pulse {
        color: var(--ui-01);
        cursor: pointer;
        background: var(--interactive-primary);
        border-radius: 50%;
        font-size: 1rem;
        height: 100%;
        position: relative;
        width: 100%;
        z-index: $z-index-bottom;

        &::after {
          content: '';
          border-radius: 50%;
          height: 100%;
          left: 0;
          position: absolute;
          top: 0;
          width: 100%;
          z-index: calc($z-index-bottom - 1);
          animation: pulse 10s infinite cubic-bezier(0.66, 0, 0, 1) 5s;
        }

        &:hover {
          background: var(--hover-primary);
          transform: scale(1.1);
        }
      }

      @keyframes pulse {
        0% {
          transform: scale(0.95);
          box-shadow: 0 0 0 0 var(--hover-primary);
        }

        20% {
          transform: scale(1);
          box-shadow: 0 0 0 10px rgba(0, 153, 255, 0);
        }

        100% {
          transform: scale(0.95);
          box-shadow: 0 0 0 0 rgba(0, 153, 255, 0);
        }
      }
    `
  ],
  template: `
    @if (events.length > 0) {
      <div class="pulse" (click)="toggleEvents()">
        {{ events.length }}
      </div>
    }
  `,
  standalone: true
})
export class SdcSseEventComponent implements OnInit, OnDestroy {
  public events: SseEventModel[] = [];

  private showEvents = false;
  private subscriptions$: Subscription[] = [];

  constructor(
    private readonly notificationService: NotificationService,
    private readonly sseService: SseService
  ) {}

  ngOnInit() {
    this.subscriptions$.push(this.eventObserver(), this.notificationObserver());
  }

  ngOnDestroy() {
    this.subscriptions$.forEach(subscription => subscription.unsubscribe());
  }

  public toggleEvents(): void {
    this.showEvents = !this.showEvents;

    this.events.forEach(event => {
      if (this.showEvents) {
        event.id =
          event.type === 'ERROR'
            ? this.notificationService.error(event.type, event.message, 0, true)
            : this.notificationService.info(event.type, event.message, 10, false);
      } else {
        event.id && this.notificationService.closeNotification(event.id);
      }
    });
  }
  private eventObserver = (): Subscription =>
    this.sseService.getServerSentEventObserver().subscribe(event => {
      this.events = [...this.events, event];
    });

  private notificationObserver = (): Subscription =>
    this.notificationService.onCloseNotification().subscribe(notificationId => {
      if (this.showEvents) this.events = this.events.filter(event => event.id !== notificationId);
    });
}
