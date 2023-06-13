import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { emptyFn } from 'src/app/core/lib';
import { UiNotificationComponent } from './notification.component';

describe('UiNotificationComponent', () => {
  let component: UiNotificationComponent;
  let fixture: ComponentFixture<UiNotificationComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [UiNotificationComponent],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UiNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the sdc notification component', () => {
    expect(component).toBeTruthy();
  });
});
