/* eslint max-classes-per-file: 0 */
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { TranslateModule } from '@ngx-translate/core';
import { LanguageService } from '../language.service';
import { SESSION_LANGUAGE_KEY } from '../constants';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';

describe('LanguageService', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [provideHttpClient(withInterceptorsFromDi()), provideHttpClientTesting()]
    })
  );

  it('should create service', () => {
    const service: LanguageService = TestBed.inject(LanguageService);
    expect(service).toBeTruthy();
  });

  it('should set the key into a variable in localStorage', () => {
    const service: LanguageService = TestBed.inject(LanguageService);
    localStorage.clear();
    service.i18n('esES');

    const stored = localStorage.getItem(SESSION_LANGUAGE_KEY);

    expect(stored).toBeTruthy();
    expect(JSON.parse(stored as string).value).toBe('esES');
  });
});
