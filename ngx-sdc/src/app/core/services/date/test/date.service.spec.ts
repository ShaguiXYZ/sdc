import { TestBed } from '@angular/core/testing';
import { UiLanguageServiceMock } from 'src/app/core/mock/services/language.service.mock';
import { UiLanguageService } from '../../language';
import { UiDateService } from '../date.service';

describe('UiDateService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{ provide: UiLanguageService, useClass: UiLanguageServiceMock }]
    });
  });

  it('should be created', () => {
    const service: UiDateService = TestBed.inject(UiDateService);
    expect(service).toBeTruthy();
  });

  it('should format a timestamp to a date string', () => {
    const service: UiDateService = TestBed.inject(UiDateService);
    const timestamp = 1628772000000; // August 12, 2021 12:00:00 AM UTC
    const expectedDateString = '8/12/2021'; // Assuming en-US locale
    const dateString = service.dateFormat(timestamp);

    expect(dateString).toEqual(expectedDateString);
  });
});
