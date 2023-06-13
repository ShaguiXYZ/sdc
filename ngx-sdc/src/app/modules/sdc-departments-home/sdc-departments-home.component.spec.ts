import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from 'src/app/core/lib';
import { SdcDepartmentsHomeComponent } from './sdc-departments-home.component';

describe('SdcDepartmentsHomeComponent', () => {
  let component: SdcDepartmentsHomeComponent;
  let fixture: ComponentFixture<SdcDepartmentsHomeComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SdcDepartmentsHomeComponent],
      imports: [HttpClientModule, RouterTestingModule, TranslateModule.forRoot()],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcDepartmentsHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the sdc department component', () => {
    expect(component).toBeTruthy();
  });

  it('should filter on the department', () => {
    component.onSearchDepartmentChanged('filter');
  });

  it('should filter on the squad', () => {
    component.onSearchSquadChanged('filter');
  });
});
