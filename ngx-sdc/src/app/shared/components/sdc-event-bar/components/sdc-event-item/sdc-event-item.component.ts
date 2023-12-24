import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { SseEventModel } from 'src/app/core/services';
import { AppUrls } from 'src/app/shared/config/routing';
import { SdcEventReference } from 'src/app/shared/models';
import { SdcEventItemService } from './services';
import { copyToClipboard } from 'src/app/core/lib';
import { NotificationService } from 'src/app/core/components';
import { SDC_DEFAULT_NOTIFICATION_DURATION } from 'src/app/shared/constants';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'sdc-event-item',
  styleUrls: ['./sdc-event-item.component.scss'],
  template: `
    <article class="event-item">
      <header>
        <span class="title {{ event.type.toLocaleLowerCase() }}">{{ event.type }}</span>
        @if (closaable) {
          <em class="sdc-op close-event fa-regular fa-circle-xmark" (click)="removeEvent()"></em>
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
  public closaable = true;

  @Input()
  public copyable = true;

  @Output()
  public onRemoveEvent: EventEmitter<SseEventModel<SdcEventReference>> = new EventEmitter();

  constructor(
    private readonly eventItemService: SdcEventItemService,
    private readonly notificationService: NotificationService,
    private readonly router: Router,
    private readonly translateService: TranslateService
  ) {}

  public onClickComponent(): void {
    // @howto: navigation to the metrics page, even if you are already on it
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.event.reference?.componentId &&
        this.eventItemService.getComponent(this.event.reference.componentId).then(component => {
          this.router.navigate([AppUrls.metrics]);
        });
    });
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
