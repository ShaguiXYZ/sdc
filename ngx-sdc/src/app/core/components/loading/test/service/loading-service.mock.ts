import { Observable } from 'rxjs';

export class UiLoadingServiceMock {
  uiShowLoading = new Observable(observer => {
    observer.next(true);
    observer.complete();
  });
}
