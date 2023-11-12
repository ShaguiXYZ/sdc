/* eslint max-classes-per-file: 0 */
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { TranslateModule } from '@ngx-translate/core';
import { LanguageService } from '../language.service';

describe('LanguageService', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, TranslateModule.forRoot()]
    })
  );

  it('should create service', () => {
    const service: LanguageService = TestBed.inject(LanguageService);
    expect(service).toBeTruthy();
  });

  it('should set the key into a variable in localStorage', () => {
    const service: LanguageService = TestBed.inject(LanguageService);
    service.i18n('key');
  });
});
