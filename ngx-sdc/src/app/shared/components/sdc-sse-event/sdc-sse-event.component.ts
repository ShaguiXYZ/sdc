// create new component
import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NxTooltipModule } from '@aposin/ng-aquila/tooltip';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { SdcSseEventService } from './services';

@Component({
    selector: 'sdc-sse-event',
    styleUrls: ['./sdc-sse-event.component.scss'],
    template: `
    <div
      class="num-events sdc-center"
      [ngClass]="{ pulse: unread, 'has-messages': count }"
      [nxTooltip]="'Label.LastAnalysisEvents' | translate: { value: count }"
      nxTooltipPosition="top"
      (click)="toggleEvents()"
    >
      {{ count }}
    </div>
  `,
    providers: [SdcSseEventService],
    imports: [CommonModule, NxTooltipModule, TranslateModule]
})
export class SdcSseEventComponent implements OnInit, OnDestroy {
  public count = 0;
  public unread = 0;
  private subscriptions$: Subscription[] = [];

  constructor(private readonly sseEventService: SdcSseEventService) {}

  ngOnInit() {
    this.subscriptions$.push(
      this.sseEventService.onDataChange().subscribe(events => {
        this.count = events.length;
        this.unread = events.filter(e => !e.read).length;
      })
    );

    this.sseEventService.loadData();
  }

  ngOnDestroy() {
    this.subscriptions$.forEach(subscription => subscription.unsubscribe());
  }

  public toggleEvents(): void {
    this.sseEventService.toggleEvents();
  }
}
