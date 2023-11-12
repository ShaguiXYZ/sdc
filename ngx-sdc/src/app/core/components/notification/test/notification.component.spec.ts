import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { emptyFn } from 'src/app/core/lib';
import { NotificationComponent } from '../notification.component';
import { NotificationService } from '../services';
import { NotificationServiceMock, notification } from './service/notification-service.mock';

describe('NotificationComponent', () => {
  let component: NotificationComponent;
  let fixture: ComponentFixture<NotificationComponent>;
  let services: any;
  let spies: any;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [NotificationComponent],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [{ provide: NotificationService, useClass: NotificationServiceMock }]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NotificationComponent);
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
      notificationService: TestBed.inject(NotificationService)
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
