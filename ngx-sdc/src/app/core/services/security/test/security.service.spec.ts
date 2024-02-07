import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { Observable, of, throwError } from 'rxjs';
import { HttpServiceMock } from 'src/app/core/mock/services/http-service.mock';
import { ContextDataService } from '../..';
import { HttpService } from '../../http';
import { ISessionModel } from '../models';
import { SecurityService } from '../security.service';
import { SecurityContextDataServiceMock, user } from './mock/context-data-service.mock';

describe('SecurityService', () => {
  let service: any;
  let services: any;
  let spies: any;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        SecurityService,
        { provide: HttpService, useClass: HttpServiceMock },
        { provide: ContextDataService, useClass: SecurityContextDataServiceMock }
      ]
    });

    service = TestBed.inject(SecurityService);
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

  it('should call _put http service when logout is called case one', () => {
    spyOn(sessionStorage, 'removeItem').and.callFake(() => {
      /* Mock method */
    });
    spies.contextData.get.and.returnValue(null);
    spies.http._put.and.returnValue(of({}));
    service.logout();
    expect(spies.http._put).toHaveBeenCalled();
  });

  it('should not call put http service when logout is called', () => {
    spyOn(sessionStorage, 'removeItem').and.callFake(() => {
      /* Mock method */
    });
    service.securityInfo = () => ({});
    service.logout();
    expect(spies.http.put).not.toHaveBeenCalled();
  });

  it('should call _put http service when logout is called case two', () => {
    spyOn(sessionStorage, 'removeItem').and.callFake(() => {
      /* Mock method */
    });
    spies.contextData.get.and.returnValue(null);
    spies.http._put.and.returnValue(throwError(() => new Error()));
    service.logout();
    expect(spies.http._put).toHaveBeenCalled();
  });

  it('should get the session when get session is called', () => {
    expect(service.session).not.toBeNull();
  });

  it('should get the user when get user is called', () => {
    expect(service.user).not.toBeNull();
  });

  it('should throw a error when set user is called and securityInfo is not defined', () => {
    service.securityInfo = () => undefined;
    expect(() => {
      service.user = user;
    }).toThrow(new Error('Not valid token returned'));
  });

  it('should return a Observable when onSignIn is called', () => {
    const result: Observable<ISessionModel> = service.onSignIn();
    expect(result).not.toBeNull();
  });

  const initServices = () => {
    services = {
      http: TestBed.inject(HttpService),
      contextData: TestBed.inject(ContextDataService)
    };
    initSpies();
  };

  const initSpies = () => {
    spies = {
      http: {
        get: spyOn(services.http, 'get'),
        put: spyOn(services.http, 'put'),
        _put: spyOn(services.http, '_put')
      },
      contextData: {
        set: spyOn(services.contextData, 'set'),
        get: spyOn(services.contextData, 'get')
      }
    };
  };
});
