import { Observable } from 'rxjs';

export class TranslateServiceMock {
  private lang = '';

  get(key: string) {
    return new Observable<any>();
  }

  instant(key: string): string {
    return key;
  }

  setDefaultLang(lang: string): void {
    this.lang = lang;
  }

  stream(key: string | Array<string>, interpolateParams?: object): Observable<string | any> {
    return new Observable<string>();
  }
}
