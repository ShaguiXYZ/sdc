import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { NotificationService } from '../notification.service';

describe('NotificationService', () => {
  let service: any;
  let spies: any;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      providers: [NotificationService]
    });

    service = TestBed.inject(NotificationService);
    initSpies();
  });

  it('should exist service when module is compiled', () => {
    expect(service).toBeTruthy();
  });

  it('should call next when error is called', () => {
    service.error('', '', 1, true);
    expect(spies.next).toHaveBeenCalled();
  });

  it('should call next when warning is called', () => {
    service.warning('', '', 1, true);
    expect(spies.next).toHaveBeenCalled();
  });

  it('should call next when info is called', () => {
    service.info('', '', 1, true);
    expect(spies.next).toHaveBeenCalled();
  });

  it('should call next when success is called', () => {
    service.success('', '', 1, true);
    expect(spies.next).toHaveBeenCalled();
  });

  it('should call asObservable when onNotification is called', () => {
    service.onNotification();
    expect(spies.asObservableNotification).toHaveBeenCalled();
  });

  it('should call asObservable when onCloseNotification is called', () => {
    service.onCloseNotification();
    expect(spies.asObservableCloseNotification).toHaveBeenCalled();
  });

  const initSpies = () => {
    spies = {
      next: spyOn(service.notification$, 'next'),
      asObservableNotification: spyOn(service.notification$, 'asObservable'),
      asObservableCloseNotification: spyOn(service.closeNotification$, 'asObservable')
    };
  };
});
