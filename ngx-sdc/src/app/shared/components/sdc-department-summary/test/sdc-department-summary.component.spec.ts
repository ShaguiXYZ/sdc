import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { emptyFn } from 'src/app/core/lib';
import { SdcDepartmentSummaryComponent } from '../sdc-department-summary.component';
import { TranslateModule } from '@ngx-translate/core';

describe('SdcDepartmentSummaryComponent', () => {
  let component: SdcDepartmentSummaryComponent;
  let fixture: ComponentFixture<SdcDepartmentSummaryComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SdcDepartmentSummaryComponent],
      imports: [HttpClientModule, RouterTestingModule, TranslateModule.forRoot()],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcDepartmentSummaryComponent);
    component = fixture.componentInstance;
    const department = { id: 1, name: '', coverage: 1 };
    component.department = department;
    component.squads = [{ id: 1, name: 'squad', department, coverage: 1 }];
    fixture.detectChanges();
  });

  it('should create the department summary component', () => {
    expect(component).toBeTruthy();
  });
});
