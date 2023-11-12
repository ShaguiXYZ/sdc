import { HttpClientModule } from '@angular/common/http';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { TranslateService } from '@ngx-translate/core';
import { AppComponent } from '../app.component';
import { UiAlertComponent, UiLoadingComponent, UiNotificationComponent } from '../core/components';
import { TranslateServiceMock } from '../core/mock/services';
import { UiStorageServiceMock } from '../core/mock/services/storage-service.mock';
import { UiStorageService } from '../core/services/context-data';

describe('AppComponent', () => {
  let services: any;
  let spies: any;
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [AppComponent],
      imports: [
        UiAlertComponent,
        UiLoadingComponent,
        UiNotificationComponent,
        BrowserAnimationsModule,
        HttpClientModule,
        RouterTestingModule
      ],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        { provide: TranslateService, useClass: TranslateServiceMock },
        { provide: UiStorageService, useClass: UiStorageServiceMock }
      ]
    }).compileComponents();
  }));

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
      preventDefault: (): string => '',
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
