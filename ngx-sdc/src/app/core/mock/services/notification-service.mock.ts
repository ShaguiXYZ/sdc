import { NotificationModel } from '@shagui/ng-shagui/core';
import { Observable } from 'rxjs';

export class NotificationServiceMock {
  onNotification = (): Observable<NotificationModel> =>
    /* Mock method */
    new Observable();

  onCloseNotification = (): Observable<string> =>
    /* Mock method */
    new Observable();

  closeNotification(): void {
    /* Mock method */
  }

  error() {
    /* Mock method */
  }
}
