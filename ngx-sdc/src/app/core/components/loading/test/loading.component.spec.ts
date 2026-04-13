import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { NxDialogService, NxModalModule } from '@allianz/ng-aquila/modal';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';
import { LoadingService, emptyFn } from '@shagui/ng-shagui/core';
import { NxDialogServiceMock } from 'src/app/core/mock/services/dialog-service.mock';
import { LoadingServiceMock } from 'src/app/core/mock/services/loading-service.mock';
import { LoadingComponent } from '../loading.component';

describe('LoadingComponent', () => {
  let component: LoadingComponent;
  let fixture: ComponentFixture<LoadingComponent>;
  let openLoadingModalSpy: jasmine.Spy;
  const loadingServiceMock = new LoadingServiceMock();

  beforeEach(waitForAsync(() => {
    spyOn(loadingServiceMock, 'asObservable').and.returnValue(of(true));

    TestBed.configureTestingModule({
      imports: [LoadingComponent, NxModalModule.forRoot(), TranslateModule.forRoot()],
      providers: [
        { provide: LoadingService, useValue: loadingServiceMock },
        { provide: NxDialogService, useClass: NxDialogServiceMock }
      ]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingComponent);
    component = fixture.componentInstance;
    openLoadingModalSpy = spyOn<any>(component, 'openLoadingModal');
    fixture.detectChanges();
  });

  it('should create the loading component', () => {
    expect(component).toBeTruthy();
  });

  it('should show loading dialog when loading service emits true', () => {
    expect(openLoadingModalSpy).toHaveBeenCalled();
  });
});
