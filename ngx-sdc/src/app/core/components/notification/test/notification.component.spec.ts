import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { emptyFn } from 'src/app/core/lib';
import { UiNotificationComponent } from '../notification.component';
import { UiNotificationService } from '../services';
import { UiNotificationServiceMock, notification } from './service/notification-service.mock';

describe('UiNotificationComponent', () => {
  let component: UiNotificationComponent;
  let fixture: ComponentFixture<UiNotificationComponent>;
  let services: any;
  let spies: any;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [UiNotificationComponent],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [{ provide: UiNotificationService, useClass: UiNotificationServiceMock }]
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

  const initServices = () => {
    services = {
      notificationService: TestBed.inject(UiNotificationService)
    };
    initSpies();
  };

  const initSpies = () => {
    spies = {
      notificationService: {
        closeNotification: spyOn(services.notificationService, 'closeNotification')
      }
    };
  };
});
