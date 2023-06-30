import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { UiAlertService } from '../alert.service';
import { TranslateService } from '@ngx-translate/core';
import { TranslateServiceMock } from 'src/app/core/mock/services';

describe(`UiAlertService`, () => {
  let service: any;
  let services: any;
  let spies: any;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      providers: [UiAlertService, { provide: TranslateService, useClass: TranslateServiceMock }]
    });

    service = TestBed.inject(UiAlertService);

    initServices();
  });

  it('should exist service when module is compiled', () => {
    expect(service).toBeTruthy();
  });

  it('should call translateService instant when confirm is called case one', () => {
    service.confirm(
      { text: '', title: '' },
      () => {
        /* Mock method */
      },
      { okText: '', cancelText: '' },
      true
    );
    expect(services.translateService.instant).toHaveBeenCalled();
  });

  it('should call translateService instant when confirm is called case two', () => {
    service.confirm(
      { text: ['test', ''] },
      () => {
        /* Mock method */
      },
      { okText: '', cancelText: '' },
      true
    );
    expect(services.translateService.instant).toHaveBeenCalled();
  });

  it('should call translateService instant when confirm is called case three', () => {
    service.confirm(
      { text: 'test', title: 'test' },
      () => {
        /* Mock method */
      },
      true
    );
    expect(services.translateService.instant).toHaveBeenCalled();
  });

  it('should return not null when onAlert is called', () => {
    const spyNext = spyOn(service.alert$, 'next');
    const result = service.closeAlert();
    expect(spyNext).toHaveBeenCalled();
  });

  it('should call alert next when closeAlert is called', () => {
    const result = service.onAlert();
    expect(result).not.toBeNull();
  });


  const initServices = () => {
    services = {
      translateService: TestBed.inject(TranslateService)
    };
    initSpies();
  };

  const initSpies = () => {
    spies = {
      translateService: {
        instant: spyOn(services.translateService, 'instant')
      }
    };
  };
});
