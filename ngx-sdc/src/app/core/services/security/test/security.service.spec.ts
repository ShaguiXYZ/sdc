import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { Observable, of, throwError } from 'rxjs';
import { HttpServiceMock } from 'src/app/core/mock/services/http-service.mock';
import { UiContextDataService } from '../..';
import { UiHttpService } from '../../http';
import { UiSecurityService } from '../security.service';
import { AppAuthorities, ISecurityModel, ISessionModel, IUserModel } from '../models';

describe(`UiSecurityService`, () => {
  let service: any;
  let services: any;
  let spies: any;
  const securityModel: ISecurityModel = { session: { sid: '', token: '' }, user: { userName: '', authorities: [{ authority: AppAuthorities.business }], email: '', name: '', surname: '', secondSurname: '' } };
  const user: IUserModel = { userName: '', authorities: [{ authority: AppAuthorities.business }], email: '', name: '', surname: '', secondSurname: '' };

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      providers: [UiSecurityService,
        { provide: UiHttpService, useClass: HttpServiceMock },
        { provide: UiContextDataService, useClass: UiContextDataServiceMock },]
    });

    service = TestBed.inject(UiSecurityService);
    service.session = {};
    service.user = {};
    initServices();
    service.changeWindowLocationHref = function () { };
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

  it('should call put http service when logout is called case one', () => {

    spyOn(sessionStorage, 'removeItem').and.callFake(() => {
      /* Mock method */
    });
    spies.contextData.get.and.returnValue(null);
    spies.http.put.and.returnValue(of({}));
    service.logout();
    expect(spies.http.put).toHaveBeenCalled();
  });

  it('should not call put http service when logout is called', () => {

    spyOn(sessionStorage, 'removeItem').and.callFake(() => {
      /* Mock method */
    });
    service.securityInfo = function () {
      return {};
    }
    service.logout();
    expect(spies.http.put).not.toHaveBeenCalled();
  });

  it('should call put http service when logout is called case two', () => {
    spyOn(sessionStorage, 'removeItem').and.callFake(() => {
      /* Mock method */
    });
    spies.contextData.get.and.returnValue(null);
    spies.http.put.and.returnValue(throwError({ status: 403 }));
    service.logout();
    expect(spies.http.put).toHaveBeenCalled();
  });

  it('should get the session when get session is called', () => {
    expect(service.session).not.toBeNull();
  });

  it('should get the user when get user is called', () => {
    expect(service.user).not.toBeNull();
  });

  it('should throw a error when set user is called and securityInfo is not defined', () => {
    service.securityInfo = function () {
      return undefined;
    }
    expect(() => {
      service.user = user;
    }).toThrow(new Error('Valid token not returned'));
  });

  it('should return true when isBusinessUser is called', () => {
    spies.contextData.get.and.returnValue(securityModel);
    const result = service.isBusinessUser();
    expect(result).toBeTrue();
  });

  it('should return false when isBusinessUser is called', () => {
    spies.contextData.get.and.returnValue(securityModel);
    const result = service.isItUser();
    expect(result).toBeFalse();
  });

  it('should return false when uidSameProfile is called', () => {
    spies.contextData.get.and.returnValue(securityModel);
    const result = service.uidSameProfile(AppAuthorities.it);
    expect(result).toBeFalse();
  });

  it('should return a Observable when onSignIn is called', () => {
    const result: Observable<ISessionModel> = service.onSignIn();
    expect(result).not.toBeNull();
  });

  it('should call get http service when uidIsItUser is called case one', () => {
    spies.http.get.and.returnValue(of([{ authority: AppAuthorities.it }, { authority: AppAuthorities.business }]));
    service.uidIsItUser(1);
    expect(spies.http.get).toHaveBeenCalled();
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
        set: spyOn(services.contextData, 'set'),
        get: spyOn(services.contextData, 'get')
      }
    };
  };

  class UiContextDataServiceMock {
    set() { };
    get() { return securityModel };
    delete() { };
  }

});
