import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/core/components';
import { copyToClipboard } from 'src/app/core/lib';
import { SseEventModel } from 'src/app/core/services';
import { SDC_DEFAULT_NOTIFICATION_DURATION } from 'src/app/shared/constants';
import { SdcEventReference } from 'src/app/shared/models';
import { SdcEventItemService } from './services';

@Component({
  selector: 'sdc-event-item',
  styleUrls: ['./sdc-event-item.component.scss'],
  template: `
    <article class="event-item">
      <header>
        <span class="title {{ event.type.toLocaleLowerCase() }}">{{ event.type }}</span>
        @if (readable) {
          <em class="sdc-op fa-regular" [ngClass]="{ 'fa-circle-check': event.read, 'fa-circle': !event.read }" (click)="readEvent()"></em>
        }
        @if (closable) {
          <em class="sdc-op fa-regular fa-circle-xmark" (click)="removeEvent()"></em>
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
              <a (click)="onClickComponent()">{{ event.reference.componentName }}</a>
            </nx-link>
          </div>
        }
      </section>
    </article>
  `,
  standalone: true,
  providers: [SdcEventItemService],
  imports: [CommonModule, NxLinkModule]
})
export class SdcEventItemComponent {
  @Input()
  public event!: SseEventModel<SdcEventReference>;

  @Input()
  public closable = true;

  @Input()
  public copyable = true;

  @Input()
  public readable = true;

  @Output()
  public onRemoveEvent: EventEmitter<SseEventModel<SdcEventReference>> = new EventEmitter();

  @Output()
  public onReadEvent: EventEmitter<SseEventModel<SdcEventReference>> = new EventEmitter();

  constructor(
    private readonly eventItemService: SdcEventItemService,
    private readonly notificationService: NotificationService,
    private readonly translateService: TranslateService
  ) {}

  @Input()
  public set timeOut(vaule: number) {
    setTimeout(() => {
      this.onRemoveEvent.emit(this.event);
    }, vaule);
  }

  public onClickComponent(): void {
    this.event.reference?.componentId && this.eventItemService.navigateTo(this.event.reference.componentId);
  }

  public readEvent(): void {
    this.onReadEvent.emit(this.event);
  }

  public removeEvent(): void {
    this.onRemoveEvent.emit(this.event);
  }

  public copyToClipboard(): void {
    copyToClipboard(this.event.message).then(() => {
      this.notificationService.info(
        this.translateService.instant('Label.CopyToClipboard'),
        this.event.message,
        SDC_DEFAULT_NOTIFICATION_DURATION,
        false
      );
    });
  }
}
