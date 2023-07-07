import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { NxDialogService, NxModalModule, NxModalRef } from '@aposin/ng-aquila/modal';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from 'src/app/core/lib';
import { NxDialogServiceMock } from '../../../mock/services/dialog-service.mock';
import { UiAlertComponent } from '../alert.component';
import { UiAlertService } from '../services';
import { UiAlertServiceMock } from './services/alert-service.mock';

describe('UiAlertComponent', () => {
  let component: UiAlertComponent;
  let fixture: ComponentFixture<UiAlertComponent>;
  let services: any, spies: any;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [UiAlertComponent],
      imports: [NxModalModule.forRoot(), TranslateModule.forRoot()],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        { provide: UiAlertService, useClass: UiAlertServiceMock },
        { provide: NxDialogService, useClass: NxDialogServiceMock },
      ]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UiAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    initServices();
  });

  it('should create the sdc alert component', () => {
    expect(component).toBeTruthy();
  });

  it('should call alertService closeAlert when actionAndClose called', () => {
    spies.dialogService.open.and.returnValue({close() { }});
    component.openDetailsModal();
    component.actionAndClose(() => { });
    expect(spies.alertService.closeAlert).toHaveBeenCalled();
  });

  const initServices = () => {
    services = {
      alertService: TestBed.inject(UiAlertService),
      dialogService: TestBed.inject(NxDialogService)
    };
    initSpies();
  };

  const initSpies = () => {
    spies = {
      alertService: {
        closeAlert: spyOn(services.alertService, 'closeAlert')
      },
      dialogService: {
        open: spyOn(services.dialogService, 'open')
      }
    };
  };
});
