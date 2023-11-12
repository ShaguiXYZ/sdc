import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { NxDialogService, NxModalModule } from '@aposin/ng-aquila/modal';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from 'src/app/core/lib';
import { NxDialogServiceMock } from 'src/app/core/mock/services/dialog-service.mock';
import { UiLoadingComponent } from '../loading.component';
import { UiLoadingService } from '../services';
import { UiLoadingServiceMock } from './service/loading-service.mock';

describe('UiLoadingComponent', () => {
  let component: UiLoadingComponent;
  let fixture: ComponentFixture<UiLoadingComponent>;
  let services: any;
  let spies: any;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [UiLoadingComponent, NxModalModule.forRoot(), TranslateModule.forRoot()],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        { provide: UiLoadingService, useClass: UiLoadingServiceMock },
        { provide: NxDialogService, useClass: NxDialogServiceMock }
      ]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UiLoadingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    initServices();
  });

  const initServices = () => {
    services = {
      dialogService: TestBed.inject(NxDialogService),
      loadingService: TestBed.inject(UiLoadingService)
    };

    initSpies();
  };

  const initSpies = () => {
    spies = {
      loadingService: {
        uiShowLoading: spyOn(services.loadingService, 'uiShowLoading')
      }
    };
  };

  it('should create the sdc loading component', () => {
    expect(component).toBeTruthy();
  });
});
