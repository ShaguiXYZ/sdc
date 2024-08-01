import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { NxDialogService, NxModalModule } from '@aposin/ng-aquila/modal';
import { TranslateModule } from '@ngx-translate/core';
import { LoadingService, emptyFn } from '@shagui/ng-shagui/core';
import { NxDialogServiceMock } from 'src/app/core/mock/services/dialog-service.mock';
import { LoadingServiceMock } from 'src/app/core/mock/services/loading-service.mock';
import { LoadingComponent } from '../loading.component';

describe('LoadingComponent', () => {
  let component: LoadingComponent;
  let fixture: ComponentFixture<LoadingComponent>;
  let services: any;
  let spies: any;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [LoadingComponent, NxModalModule.forRoot(), TranslateModule.forRoot()],
      providers: [
        { provide: LoadingService, useClass: LoadingServiceMock },
        { provide: NxDialogService, useClass: NxDialogServiceMock }
      ]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    initServices();
  });

  it('should create the loading component', () => {
    expect(component).toBeTruthy();
  });

  it('should show loading dialog when loading service emits true', () => {
    spies.loadingService.asObservableNotification.and.returnValue(true);
    expect(services.dialogService.open).toHaveBeenCalled();
  });

  const initServices = () => {
    services = {
      dialogService: TestBed.inject(NxDialogService),
      loadingService: TestBed.inject(LoadingService)
    };

    initSpies();
  };

  const initSpies = () => {
    spies = {
      loadingService: {
        asObservableNotification: spyOn(services.loadingService, 'asObservable')
      }
    };
  };
});
