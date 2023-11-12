import { of } from 'rxjs';
import { NotificationModel } from '../../models';

export const notification: NotificationModel = { id: '', title: '', description: '', timeout: 1, closable: true, type: 'info' };

export class NotificationServiceMock {
  onNotification() {
    return of(notification);
  }
  onCloseNotification() {
    return of('');
  }
  closeNotification() {
    /* Mock method */
  }
}
