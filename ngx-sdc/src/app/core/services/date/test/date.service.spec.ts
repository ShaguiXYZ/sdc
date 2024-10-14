import { TestBed } from '@angular/core/testing';
import { LanguageServiceMock } from 'src/app/core/mock/services/language.service.mock';
import { LanguageService } from '../../language';
import { DateService } from '../date.service';

describe('DateService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{ provide: LanguageService, useClass: LanguageServiceMock }]
    });
  });

  it('should be created', () => {
    const service: DateService = TestBed.inject(DateService);
    expect(service).toBeTruthy();
  });

  it('should format a timestamp to a date string', () => {
    const service: DateService = TestBed.inject(DateService);
    const timestamp = 1628772000000; // August 12, 2021 12:00:00 AM UTC
    const expectedDateString = '8/12/2021'; // Assuming en-US locale
    const dateString = service.format(timestamp);

    expect(dateString).toEqual(expectedDateString);
  });
});
