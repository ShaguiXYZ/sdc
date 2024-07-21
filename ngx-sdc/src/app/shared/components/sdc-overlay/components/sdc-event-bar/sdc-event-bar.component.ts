import { CommonModule } from '@angular/common';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { AlertService } from 'src/app/core/components';
import { SseEventModel } from 'src/app/core/services';
import { OverlayItemStatus } from '../../models';
import { SdcEventItemComponent } from './components';
import { DEFAULT_TIMEOUT_EVENT } from './constants';
import { SdcEventBarData } from './models';
import { SdcEventBarService } from './services';

@Component({
  selector: 'sdc-event-bar',
  styleUrls: ['./sdc-event-bar.component.scss'],
  template: `
    <div class="event-content {{ state }}">
      @if (eventBarData.events.length) {
        <header class="event-bar-options">
          <div class="event-bar-options__left">
            <em class="sdc-op fa-solid fa-chevron-down" (click)="close()"></em>
          </div>
          <div class="event-bar-options__end">
            <em class="sdc-op fa-solid fa-check-double" (click)="markAllAsRead()"></em>
            <!-- <em class="sdc-op fa-regular fa-trash-can" (click)="clearEvents()"></em> -->
          </div>
        </header>

        <article class="event-bar-items" [class.sdc-scrollable]="state === 'open'">
          @for (event of eventBarData.events; track event.id) {
            @defer (on viewport) {
              <div class="reveal event-item">
                <sdc-event-item
                  [event]="event"
                  [timeout]="event.read ? DEFAULT_TIMEOUT_EVENT : 0"
                  (readEvent)="onReadEvent($event)"
                  (removeEvent)="onRemoveEvent($event)"
                />
              </div>
            } @placeholder {
              <div class="placeholder"></div>
            }
          }
        </article>
      } @else {
        <sdc-event-item
          [event]="{ type: 'INFO', message: 'Label.NoEventsAvailables' | translate, date: now }"
          [copyable]="false"
          [closable]="false"
          [readable]="false"
        />
      }
    </div>
  `,
  providers: [SdcEventBarService],
  standalone: true,
  imports: [CommonModule, SdcEventItemComponent, TranslateModule]
})
export class SdcEventBarComponent implements OnInit, OnDestroy {
  public DEFAULT_TIMEOUT_EVENT = DEFAULT_TIMEOUT_EVENT;
  public eventBarData: SdcEventBarData = { events: [] };
  public now = Date.now();

  private _state: OverlayItemStatus = 'closed';
  private _timeout?: NodeJS.Timeout;
  private subscriptions$: Subscription[] = [];

  constructor(
    private readonly alertService: AlertService,
    private readonly eventBarService: SdcEventBarService
  ) {}

  public get state(): OverlayItemStatus {
    return this._state;
  }
  @Input()
  public set state(value: OverlayItemStatus) {
    this._state = value;

    this.controlEmptyEvents();
  }

  ngOnInit(): void {
    this.subscriptions$.push(
      this.eventBarService.onDataChange().subscribe(events => {
        Promise.resolve().then(() => {
          this.eventBarData = { ...this.eventBarData, ...events };
          this.controlEmptyEvents();
        });
      })
    );

    this.eventBarService.initialize();
  }

  ngOnDestroy(): void {
    this.subscriptions$.forEach(subscription => subscription.unsubscribe());
  }

  public onRemoveEvent(event: SseEventModel): void {
    this.eventBarService.removeEvent(event);
  }

  public onReadEvent(event: SseEventModel): void {
    this.eventBarService.readEvent(event);
  }

  public close(): void {
    this.eventBarService.toggleEvents();
  }

  public markAllAsRead(): void {
    this.eventBarService.markAllAsRead();
  }

  public clearEvents(): void {
    this.alertService.confirm(
      {
        title: 'Alerts.ClearEvents.Title',
        text: 'Alerts.ClearEvents.Description'
      },
      this.eventBarService.clearEvents.bind(this.eventBarService),
      { okText: 'Label.Yes', cancelText: 'Label.No' }
    );
  }

  private controlEmptyEvents(): NodeJS.Timeout {
    this._timeout && clearTimeout(this._timeout);

    return setTimeout(() => {
      !this.eventBarData.events.length && this._state === 'open' && this.close();
    }, 5000);
  }
}
