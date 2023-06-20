import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { UiSecurityService } from './security.service';
import { UiHttpService } from '../http';
import { of } from 'rxjs';

describe(`UiSecurityService`, () => {
  let service: any, services: any, spies: any;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        UiSecurityService,
        { provide: UiHttpService, useClass: HttpClientMock }
      ]
    });

    service = TestBed.inject(UiSecurityService);

    initServices();
  });

  it('should exist service when module is compiled', () => {
    expect(service).toBeTruthy();
  });

  it('should have injected correct services', () => {
    expect(service.http).toEqual(services.http);
  });

  it('should call get http service when authUser is called', async () => {
    spies.http.get.and.returnValue(of(''));
    await service.authUser();
    expect(spies.http.get).toHaveBeenCalled();
  });

  it('should call get http service when uidIsItUser is called', async () => {
    spies.http.get.and.returnValue(of([]));
    await service.uidIsItUser();
    expect(spies.http.get).toHaveBeenCalled();
  });

  function initServices() {
    services = {
      http: TestBed.inject(UiHttpService)
    };
    initSpies();
  }

  function initSpies() {
    spies = {
      http: {
        get: spyOn(services.http, 'get'),
        put: spyOn(services.http, 'put')
      }
    };
  }

  class HttpClientMock {
    get() {
      return null;
    }
    put() {
      return null;
    }
  }

});
