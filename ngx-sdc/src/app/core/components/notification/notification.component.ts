import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxMessageModule } from '@aposin/ng-aquila/message';
import { Subscription } from 'rxjs';
import { NotificationModel } from './models';
import { NotificationService } from './services';

@Component({
  selector: 'nx-notification',
  styleUrls: ['./notification.component.scss'],
  templateUrl: './notification.component.html',
  standalone: true,
  imports: [CommonModule, NxButtonModule, NxMessageModule]
})
export class NotificationComponent implements OnInit, OnDestroy {
  public notifications: NotificationModel[] = [];
  private notificationSubscription!: Subscription;
  private closeNotificationSubscription!: Subscription;

  constructor(private notificationService: NotificationService) {}

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
