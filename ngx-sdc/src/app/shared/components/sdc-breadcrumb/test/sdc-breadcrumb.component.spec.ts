import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { SpyLocation } from '@angular/common/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from 'src/app/core/lib';
import { SdcBreadcrumbComponent } from '../sdc-breadcrumb.component';

describe('SdcBreadcrumbComponent', () => {
  let component: SdcBreadcrumbComponent;
  let fixture: ComponentFixture<SdcBreadcrumbComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SdcBreadcrumbComponent],
      imports: [HttpClientModule, RouterTestingModule, TranslateModule.forRoot()],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [{ provide: Location, useClass: SpyLocation }]
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