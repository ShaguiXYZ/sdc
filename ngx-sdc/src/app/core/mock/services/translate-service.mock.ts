/* eslint-disable @typescript-eslint/no-unused-vars */
import { Observable } from 'rxjs';

export class TranslateServiceMock {
  private lang = '';

  get(key: string) {
    return new Observable<unknown>();
  }

  instant(key: string): string {
    return key;
  }

  setDefaultLang(lang: string): void {
    this.lang = lang;
  }

  stream(key: string | Array<string>, interpolateParams?: object): Observable<string | undefined> {
    return new Observable<string>();
  }
}
