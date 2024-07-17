import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { SpyLocation } from '@angular/common/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from '@shagui/ng-shagui/core';
import { SdcBreadcrumbComponent } from '../sdc-breadcrumb.component';

describe('SdcBreadcrumbComponent', () => {
  let component: SdcBreadcrumbComponent;
  let fixture: ComponentFixture<SdcBreadcrumbComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [RouterTestingModule, TranslateModule.forRoot(), SdcBreadcrumbComponent],
    providers: [{ provide: Location, useClass: SpyLocation }, provideHttpClient(withInterceptorsFromDi())]
})
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcBreadcrumbComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the sdc breadcrum component', () => {
    expect(component).toBeTruthy();
  });

  // it('should go back to previous location on button click', () => {
  //   spyOn<any>(component, 'location.back');
  //   component.backClicked();
  //   expect(component['location'].back).toHaveBeenCalled();
  // });
});
