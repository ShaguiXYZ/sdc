import { Observable } from 'rxjs';

export class LoadingServiceMock {
  public asObservable(): Observable<boolean> {
    /* Mocked Functionality */
    return new Observable<boolean>();
  }
}
