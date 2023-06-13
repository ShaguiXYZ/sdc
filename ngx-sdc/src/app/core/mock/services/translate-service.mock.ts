import { Observable } from 'rxjs';

export class MockTranslateService {
  private lang = '';

  setDefaultLang(lang: string): void {
    this.lang = lang;
  }

  stream(key: string | Array<string>, interpolateParams?: object): Observable<string | any> {
    return new Observable<string>();
  }

  instant(key: string): string {
    return key;
  }

  get(key: string) {
    return new Observable<any>();
  }
}
