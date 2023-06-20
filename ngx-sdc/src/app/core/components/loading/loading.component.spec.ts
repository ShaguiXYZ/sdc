import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { NxDialogService, NxModalModule } from '@aposin/ng-aquila/modal';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from 'src/app/core/lib';
import { UiLoadingComponent } from './loading.component';
import { UiLoadingService } from './services';
import { Observable } from 'rxjs';

describe('UiLoadingComponent', () => {
  let component: UiLoadingComponent;
  let fixture: ComponentFixture<UiLoadingComponent>;
  let services: any, spies: any;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [UiLoadingComponent],
      imports: [NxModalModule.forRoot(), TranslateModule.forRoot()],
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

  function initServices() {
    services = {
      dialogService: TestBed.inject(NxDialogService),
      loadingService: TestBed.inject(UiLoadingService)
    };

    initSpies();
  }

  function initSpies() {
    spies = {
      loadingService: {
        uiShowLoading: spyOn(services.loadingService, 'uiShowLoading')
      }
    };
  }

  it('should create the sdc loading component', () => {
    expect(component).toBeTruthy();
  });
});

class NxDialogServiceMock {
  open() { };
}

class UiLoadingServiceMock {
  uiShowLoading = new Observable(observer => {
    observer.next(true);
    observer.complete();
  });
}
