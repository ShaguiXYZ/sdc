/* eslint max-classes-per-file: 0 */
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs/internal/Observable';
import { UiLanguageService } from './language.service';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

interface DataMock {
  id: number;
  name: string;
}

describe('UiLanguageService', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, TranslateModule.forRoot()]
    })
  );

  it('should create service', () => {
    const service: UiLanguageService = TestBed.inject(UiLanguageService);
    expect(service).toBeTruthy();
  });

  it('should set the key into a variable in localStorage', () => {
    const service: UiLanguageService = TestBed.inject(UiLanguageService);
    service.i18n('key');
  });
});
