import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnDestroy, Output } from '@angular/core';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { TranslateService } from '@ngx-translate/core';
import { DEFAULT_TIMEOUT_NOTIFICATIONS, NotificationService, copyToClipboard } from '@shagui/ng-shagui/core';
import { SseEventModel } from 'src/app/core/services';
import { SdcEventItemService } from './services';

@Component({
  selector: 'sdc-event-item',
  styleUrls: ['./sdc-event-item.component.scss'],
  template: `
    <article class="event-item" [class.fade-out]="fadeOut">
      <header>
        <span class="title {{ event.type.toLocaleLowerCase() }}">{{ event.type }}</span>
        @if (readable) {
          <em
            class="sdc-op fa-regular"
            [ngClass]="{ 'fa-circle-check': event.read, 'fa-circle': !event.read }"
            (click)="onReadEvent()"
          ></em>
        }
        @if (closable) {
          <em class="sdc-op fa-regular fa-circle-xmark" (click)="onRemoveEvent()"></em>
        }
      </header>
      <section>
        <div class="sdc-event-item__date">{{ event.date | date: 'medium' }}</div>
        <div class="sdc-event-item__message">
          {{ event.message }}
          @if (copyable) {
            <em class="sdc-op copy-event fa-regular fa-copy" (click)="copyToClipboard()"></em>
          }
        </div>
        @if (event.reference) {
          <div class="sdc-event-item__reference">
            <nx-link>
              <a (click)="onClickComponent()"
                ><span>{{ event.reference.componentName }}</span></a
              >
            </nx-link>
            @if (event.reference.metricName) {
              <span class="sdc-event-item__metric">({{ event.reference.metricName }})</span>
            }
          </div>
        }
      </section>
    </article>
  `,
  standalone: true,
  providers: [SdcEventItemService],
  imports: [CommonModule, NxLinkModule]
})
export class SdcEventItemComponent implements OnDestroy {
  @Input()
  public event!: SseEventModel;

  @Input()
  public closable = true;

  @Input()
  public copyable = true;

  @Input()
  public readable = true;

  @Output()
  public removeEvent: EventEmitter<SseEventModel> = new EventEmitter();

  @Output()
  public readEvent: EventEmitter<SseEventModel> = new EventEmitter();

  public fadeOut = false;

  private _timeout?: NodeJS.Timeout;
  private fadeOutTimeout?: NodeJS.Timeout;

  constructor(
    private readonly eventItemService: SdcEventItemService,
    private readonly notificationService: NotificationService,
    private readonly translateService: TranslateService
  ) {}

  @Input()
  public set timeout(value: number) {
    this._timeout && clearTimeout(this._timeout);

    if (value) {
      this._timeout = setTimeout(() => {
        this.onRemoveEvent();
      }, value);
    }
  }

  ngOnDestroy(): void {
    this._timeout && clearTimeout(this._timeout);
    this.fadeOutTimeout && clearTimeout(this.fadeOutTimeout);
  }

  public onClickComponent(): void {
    this.event.reference?.componentId && this.eventItemService.navigateTo(this.event.reference.componentId);
  }

  public onReadEvent(): void {
    this.readEvent.emit(this.event);
  }

  public onRemoveEvent(): void {
    this.fadeOut = true;

    this.fadeOutTimeout = setTimeout(() => {
      this.removeEvent.emit(this.event);
    }, 700);
  }

  public copyToClipboard(): void {
    copyToClipboard(this.event.message).then(() => {
      this.notificationService.info(
        this.translateService.instant('Label.CopyToClipboard'),
        this.event.message,
        DEFAULT_TIMEOUT_NOTIFICATIONS,
        false
      );
    });
  }
}
