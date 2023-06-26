import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpServiceMock } from 'src/app/core/mock/services/http-service.mock';
import { UiContextDataService } from '../..';
import { UiHttpService } from '../../http';
import { UiSecurityService } from '../security.service';

describe(`UiSecurityService`, () => {
  let service: any;
  let services: any;
  let spies: any;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      providers: [UiSecurityService, { provide: UiHttpService, useClass: HttpServiceMock }]
    });

    service = TestBed.inject(UiSecurityService);
    service.session = {};
    service.user = {};
    initServices();
  });

  it('should exist service when module is compiled', () => {
    expect(service).toBeTruthy();
  });

  it('should have injected correct services', () => {
    expect(service.http).toEqual(services.http);
  });

  it('should call get http service when authUser is called', () => {
    spies.http.get.and.returnValue(of(''));
    service.authUser();
    expect(spies.http.get).toHaveBeenCalled();
  });

  it('should call get http service when uidIsItUser is called', () => {
    spies.http.get.and.returnValue(of([]));
    service.uidIsItUser();
    expect(spies.http.get).toHaveBeenCalled();
  });

  it('should call put http service when logout is called', () => {
    spyOn(sessionStorage, 'removeItem').and.callFake(() => {
      /* Mock method */
    });
    spies.contextData.get.and.returnValue(null);
    spies.http.put.and.returnValue({
      subscribe: () => {
        /* Mock method */
      }
    });
    service.logout();
    expect(spies.http.put).toHaveBeenCalled();
  });

  const initServices = () => {
    services = {
      http: TestBed.inject(UiHttpService),
      contextData: TestBed.inject(UiContextDataService)
    };
    initSpies();
  };

  const initSpies = () => {
    spies = {
      http: {
        get: spyOn(services.http, 'get'),
        put: spyOn(services.http, 'put')
      },
      contextData: {
        get: spyOn(services.contextData, 'get'),
        set: spyOn(services.contextData, 'set'),
        delete: spyOn(services.contextData, 'delete')
      }
    };
  };
});
