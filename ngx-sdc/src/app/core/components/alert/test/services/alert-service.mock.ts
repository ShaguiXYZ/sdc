import { of } from 'rxjs';

export class UiAlertServiceMock {
  closeAlert() {
    /* Mock method */
  }

  onAlert() {
    return of({ descriptions: ['test'] });
  }
}
