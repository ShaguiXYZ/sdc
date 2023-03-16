import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { UiNotificationService } from 'src/app/core/components/notification/services/notification.service';
import { NotificationModel } from '../../../shared/models/notification.model';

@Component({
  selector: 'ui-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class UiNotificationComponent implements OnInit, OnDestroy {
  public notifications: NotificationModel[] = [];
  private notificationSubscription!: Subscription;
  private closeNotificationSubscription!: Subscription;

  constructor(private notificationService: UiNotificationService) {}

  ngOnInit(): void {
    this.notificationSubscription = this.notificationService.onNotification().subscribe(notification => {
      // If there is already a notification with the same title, description and type close it and open again
      // to avoid having duplicate notifications
      this.notifications = this.notifications.filter(
        item => item.title !== notification.title || item.description !== notification.description || item.type !== notification.type
      );

      this.notifications.push(notification);
    });

    this.closeNotificationSubscription = this.notificationService.onCloseNotification().subscribe(notificationId => {
      this.notifications = this.notifications.filter(item => item.id !== notificationId);
    });
  }

  ngOnDestroy(): void {
    this.notificationSubscription.unsubscribe();
    this.closeNotificationSubscription.unsubscribe();
  }

  public close(notificationId: string): void {
    this.notificationService.closeNotification(notificationId);
  }
}
