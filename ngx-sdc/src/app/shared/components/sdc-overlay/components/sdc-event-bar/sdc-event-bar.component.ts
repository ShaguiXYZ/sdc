import { CommonModule } from '@angular/common';
import { Component, inject, Input, OnDestroy, OnInit, signal, WritableSignal } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { AlertService } from 'src/app/core/components';
import { SseEventModel } from 'src/app/core/services';
import { OverlayItemStatus } from '../../models';
import { SdcEventItemComponent } from './components';
import { DEFAULT_TIMEOUT_EVENT } from './constants';
import { SdcEventBarService } from './services';

@Component({
  selector: 'sdc-event-bar',
  styleUrls: ['./sdc-event-bar.component.scss'],
  templateUrl: './sdc-event-bar.component.html',
  providers: [SdcEventBarService],
  imports: [CommonModule, SdcEventItemComponent, TranslateModule]
})
export class SdcEventBarComponent implements OnInit, OnDestroy {
  public DEFAULT_TIMEOUT_EVENT = DEFAULT_TIMEOUT_EVENT;
  public events: WritableSignal<SseEventModel[]> = signal([]);
  public now = Date.now();

  private _state: OverlayItemStatus = 'closed';
  private _timeout?: NodeJS.Timeout;
  private subscriptions$: Subscription[] = [];

  private readonly alertService = inject(AlertService);
  private readonly eventBarService = inject(SdcEventBarService);

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
      this.eventBarService.onDataChange().subscribe(data => {
        this.events.set(data.events ?? []);
        this.controlEmptyEvents();
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
      !this.events().length && this._state === 'open' && this.close();
    }, 5000);
  }
}
