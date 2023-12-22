// create new component
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { NotificationService } from 'src/app/core/components';
import { SseEventModel, SseService } from 'src/app/core/services';

@Component({
  selector: 'sdc-sse-event',
  styles: [
    `
      .sdc-sse-event {
        border: 1px solid black;
        border-radius: 100%;
        cursor: pointer;
        top: 10px;
        height: 20px;
        position: absolute;
        right: 10px;
        width: 20px;
      }
    `
  ],
  template: `
    @if (events.length > 0) {
      <div class="sdc-sse-event" (click)="showEvents()"></div>
    }
  `,
  standalone: true
})
export class SdcSseEventComponent implements OnInit, OnDestroy {
  public events: SseEventModel[] = [];
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

  public showEvents(): void {
    this.events.forEach(event => {
      if (event.type === 'ERROR') {
        event.id = this.notificationService.error(event.type, event.message, 0, true);
      } else {
        event.id = this.notificationService.info(event.type, event.message, 10, false);
      }
    });
  }

  private eventObserver = (): Subscription =>
    this.sseService.getServerSentEventObserver().subscribe(event => {
      this.events = [...this.events, event];
    });

  private notificationObserver = (): Subscription =>
    this.notificationService.onCloseNotification().subscribe(notificationId => {
      this.events = this.events.filter(event => event.id !== notificationId);
    });
}
