// create new component
import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { SdcSseEventService } from './services';

@Component({
  selector: 'sdc-sse-event',
  styleUrls: ['./sdc-sse-event.component.scss'],
  template: `
    <div
      class="sdc-center"
      [ngClass]="{ pulse: count }"
      (click)="toggleEvents()"
      [attr.data-title]="'Label.LastAnalysisEvents' | translate: { value: count }"
    >
      {{ count }}
    </div>
  `,
  providers: [SdcSseEventService],
  standalone: true,
  imports: [CommonModule, TranslateModule]
})
export class SdcSseEventComponent implements OnInit, OnDestroy {
  public count = 0;
  private subscriptions$: Subscription[] = [];

  constructor(private readonly sseEventService: SdcSseEventService) {}

  ngOnInit() {
    this.subscriptions$.push(
      this.sseEventService.onDataChange().subscribe(events => {
        this.count = events.length;
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
