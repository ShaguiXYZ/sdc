/* eslint max-classes-per-file: 0 */
import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { TranslateService } from '@ngx-translate/core';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { AppComponent } from './app.component';
import { UiAlertModule } from './core/components/alert/alert.module';
import { UiLoadingModule } from './core/components/loading/loading.module';
import { UiNotificationModule } from './core/components/notification/notification.module';
import { UiStorageService } from './core/services/context-data';

describe('AppComponent', () => {
  let services: any, spies: any;
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  beforeEach(
    waitForAsync(() => {
      TestBed.configureTestingModule({
        declarations: [AppComponent],
        imports: [HttpClientModule, RouterTestingModule, UiLoadingModule, UiAlertModule, UiNotificationModule],
        schemas: [NO_ERRORS_SCHEMA],
        providers: [
          { provide: TranslateService, useClass: MockTranslateService },
          { provide: UiStorageService, useClass: UiStorageServiceMock }]
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    initServices();
  });

  it('should create the app', () => {
    expect(component).toBeTruthy();
  });

  it('should call storageService create when beforeunloadHandler is called', () => {
    component.beforeunloadHandler({
      preventDefault(): string {
        return '';
      },
      returnValue: ''
    });
    expect(spies.storageService.create).toHaveBeenCalledTimes(2);
  });

  it('should call event stopPropagation when onPopState is called', () => {
    const popStateEvent = new PopStateEvent('');
    const spyStopPropagation = spyOn(popStateEvent, 'stopPropagation');
    component.onPopState(popStateEvent);
    expect(spyStopPropagation).toHaveBeenCalled();
  });

  it('should call event preventDefault when onKeyDown is called case one', () => {
    const keyBoardEvent = new KeyboardEvent('');
    spyOnProperty(keyBoardEvent, 'ctrlKey').and.returnValue(true);
    spyOnProperty(keyBoardEvent, 'key').and.returnValue('s');
    const spyPreventDefault = spyOn(keyBoardEvent, 'preventDefault');
    component.onKeyDown(keyBoardEvent);
    expect(spyPreventDefault).toHaveBeenCalled();
  });

  it('should call event preventDefault when onKeyDown is called case two', () => {
    const keyBoardEvent = new KeyboardEvent('');
    spyOnProperty(keyBoardEvent, 'metaKey').and.returnValue(true);
    spyOnProperty(keyBoardEvent, 'key').and.returnValue('s');
    spyOnProperty(navigator, 'userAgent').and.returnValue('');
    const spyPreventDefault = spyOn(keyBoardEvent, 'preventDefault');
    component.onKeyDown(keyBoardEvent);
    expect(spyPreventDefault).toHaveBeenCalled();
  });

  const initServices = () => {
    services = {
      storageService: TestBed.inject(UiStorageService)
    };
    initSpies();
  };

  const initSpies = () => {
    spies = {
      storageService: {
        create: spyOn(services.storageService, 'create')
      }
    };
  };
});

class UiStorageServiceMock {
  retrieve() { }
  create() { }
}

class MockTranslateService {
  setDefaultLang() { }
  stream() { }
  instant() { }
  get() { }
}
