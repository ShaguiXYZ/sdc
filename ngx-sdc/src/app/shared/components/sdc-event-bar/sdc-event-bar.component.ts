import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { SseEventModel } from 'src/app/core/services';
import { SdcEventReference } from '../../models';
import { SdcEventItemComponent } from './components';
import { SdcEventBarData } from './models';
import { SdcEventBarService } from './services';

@Component({
  selector: 'sdc-event-bar',
  styleUrls: ['./sdc-event-bar.component.scss'],
  template: `
    <div class="event-content {{ eventBarData.state }}" [class.sdc-scrollable]="eventBarData.state === 'open'">
      @if (eventBarData.events.length) {
        @for (event of eventBarData.events; track event.id) {
          @defer (on viewport) {
            <article class="reveal event-item">
              <sdc-event-item [event]="event" (onRemoveEvent)="onRemoveEvent($event)" />
            </article>
          } @placeholder {
            <div class="placeholder"></div>
          }
        }
      } @else {
        <sdc-event-item
          [event]="{ type: 'INFO', message: 'Label.NoEventsAvailables' | translate, date: '' }"
          [copyable]="false"
          [closaable]="false"
        />
      }
    </div>
  `,
  providers: [SdcEventBarService],
  standalone: true,
  imports: [CommonModule, SdcEventItemComponent, TranslateModule]
})
export class SdcEventBarComponent implements OnInit, OnDestroy {
  public eventBarData: SdcEventBarData = { events: [], state: 'closed' };

  private subscriptions$: Subscription[] = [];

  constructor(private eventBarService: SdcEventBarService) {}

  ngOnInit(): void {
    this.subscriptions$.push(
      this.eventBarService.onDataChange().subscribe(events =>
        Promise.resolve().then(() => {
          this.eventBarData = { ...this.eventBarData, ...events };
        })
      )
    );
  }

  ngOnDestroy(): void {
    this.subscriptions$.forEach(subscription => subscription.unsubscribe());
  }

  public onRemoveEvent(event: SseEventModel<SdcEventReference>): void {
    this.eventBarService.removeEvent(event);
  }
}
