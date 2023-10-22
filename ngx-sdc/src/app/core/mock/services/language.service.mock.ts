import { Observable } from 'rxjs';

export class UiLanguageServiceMock {
  public i18n(key: string): void {
    /* Mocked Functionality */
  }

  public getLang(): string {
    /* Mocked Functionality */
    return 'en-US';
  }

  public getLangKey(): string {
    /* Mocked Functionality */
    return 'enUS';
  }

  public getLanguages(): { [key: string]: string } {
    /* Mocked Functionality */
    return { enUS: 'en-US' };
  }

  public asObservable(): Observable<string> {
    /* Mocked Functionality */
    return new Observable<string>();
  }
}
