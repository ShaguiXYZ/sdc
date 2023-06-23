import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { emptyFn } from 'src/app/core/lib';
import { UiNotificationComponent } from './notification.component';
import { UiNotificationService } from './services';
import { of } from 'rxjs';
import { NotificationModel } from './models';

const notification: NotificationModel = { id: '', title: '', description: '', timeout: 1, closable: true, type: 'info' };

describe('UiNotificationComponent', () => {
  let component: UiNotificationComponent;
  let fixture: ComponentFixture<UiNotificationComponent>;
  let services: any, spies: any;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [UiNotificationComponent],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        { provide: UiNotificationService, useClass: UiNotificationServiceMock }
      ]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UiNotificationComponent);
    component = fixture.componentInstance;
    component.notifications = [notification];
    fixture.detectChanges();
    initServices();
  });

  it('should create the sdc notification component', () => {
    expect(component).toBeTruthy();
  });

  it('should call closeNotification when close is called', () => {
    component.close('');
    expect(spies.notificationService.closeNotification).toHaveBeenCalled();
  });

  function initServices() {
    services = {
      notificationService: TestBed.inject(UiNotificationService)
    };
    initSpies();
  }

  function initSpies() {
    spies = {
      notificationService: {
        closeNotification: spyOn(services.notificationService, 'closeNotification')
      }
    };
  }
});

class UiNotificationServiceMock {
  onNotification() { return of(notification) }
  onCloseNotification() { return of('') }
  closeNotification() { }
}
