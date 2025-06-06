import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { emptyFn } from '@shagui/ng-shagui/core';
import { NgxEchartsModule } from 'ngx-echarts';
import { SdcHorizontalBarChartComponent } from '../sdc-horizontal-bar-chart.component';

describe('SdcHorizontalBarChartComponent', () => {
  let component: SdcHorizontalBarChartComponent;
  let fixture: ComponentFixture<SdcHorizontalBarChartComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [SdcHorizontalBarChartComponent,
        RouterTestingModule,
        NgxEchartsModule.forRoot({
            echarts: () => import('echarts')
        })],
    providers: [provideHttpClient(withInterceptorsFromDi())]
})
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcHorizontalBarChartComponent);
    component = fixture.componentInstance;
    component.name = 'test';
    component.size = { height: 1, width: 1 };
    component.config = { axis: {}, data: [{ values: { value: '0' } }, { values: [] }, { values: [] }] };
    fixture.detectChanges();
  });

  it('should create the sdc horizontal bar chart component', () => {
    expect(component).toBeTruthy();
  });
});
