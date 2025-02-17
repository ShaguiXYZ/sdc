import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from '@shagui/ng-shagui/core';
import * as echarts from 'echarts/core';
import { provideEchartsCore } from 'ngx-echarts';
import { SdcDepartmentSummaryComponent } from '../sdc-department-summary.component';

describe('SdcDepartmentSummaryComponent', () => {
  let component: SdcDepartmentSummaryComponent;
  let fixture: ComponentFixture<SdcDepartmentSummaryComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      imports: [SdcDepartmentSummaryComponent, RouterTestingModule, TranslateModule.forRoot(), provideEchartsCore({ echarts })],
      providers: [provideHttpClient(withInterceptorsFromDi())]
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
