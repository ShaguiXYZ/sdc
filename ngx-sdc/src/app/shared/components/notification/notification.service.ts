import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { NotificationModel, NotificationType } from 'src/app/shared/models/notification.model';

@Injectable({
  providedIn: 'root'
})
export class UiNotificationService {
  private notification$ = new Subject<NotificationModel>();
  private closeNotification$ = new Subject<string>();

  // enable subscribing to notifications observable
  public onNotification(): Observable<NotificationModel> {
    return this.notification$.asObservable();
  }

  // enable subscribing to close notifications observable
  public onCloseNotification(): Observable<string> {
    return this.closeNotification$.asObservable();
  }

  /**
   * Creates an error notification and close it after 'timeout' milliseconds
   *
   * @param title of the notification
   * @param description of the notification
   * @param timeout optional parameter for closing the notification after 'timeout' milliseconds
   * @param closable
   * @return the notification id that can be used to close it
   */
  public error(title: string, description: string, timeout?: number, closable?: boolean): string {
    return this.notify(new NotificationModel(title, description, NotificationType.ERROR, timeout, closable));
  }

  /**
   * Creates a warning notification and close it after 'timeout' milliseconds
   *
   * @param title of the notification
   * @param description of the notification
   * @param timeout optional parameter for closing the notification after 'timeout' milliseconds
   * @param closable
   * @return the notification id that can be used to close it
   */
  public warning(title: string, description: string, timeout?: number, closable?: boolean): string {
    return this.notify(new NotificationModel(title, description, NotificationType.WARNING, timeout, closable));
  }

  /**
   * Creates an info notification and close it after 'timeout' milliseconds
   *
   * @param title of the notification
   * @param description of the notification
   * @param timeout optional parameter for closing the notification after 'timeout' milliseconds
   * @param closable
   * @return the notification id that can be used to close it
   */
  public info(title: string, description: string, timeout?: number, closable?: boolean): string {
    return this.notify(new NotificationModel(title, description, NotificationType.INFO, timeout, closable));
  }

  /**
   * Creates a success notification and close it after 'timeout' milliseconds
   *
   * @param title of the notification
   * @param description of the notification
   * @param timeout optional parameter for closing the notification after 'timeout' milliseconds
   * @param closable
   * @return the notification id that can be used to close it
   */
  public success(title: string, description: string, timeout?: number, closable?: boolean): string {
    return this.notify(new NotificationModel(title, description, NotificationType.SUCCESS, timeout, closable));
  }

  /**
   * Closes the notification
   *
   * @param notificationId
   */
  public closeNotification(notificationId: string): void {
    this.closeNotification$.next(notificationId);
  }

  private notify(notification: NotificationModel): string {
    this.notification$.next(notification);

    if (notification.timeout > 0) {
      setTimeout(() => this.closeNotification(notification.id), notification.timeout);
    }

    return notification.id;
  }
}
