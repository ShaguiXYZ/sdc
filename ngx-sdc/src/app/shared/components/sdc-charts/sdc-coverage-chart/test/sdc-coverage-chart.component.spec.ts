import { CommonModule } from '@angular/common';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { emptyFn } from '@shagui/ng-shagui/core';
import { NgxEchartsModule } from 'ngx-echarts';
import { SdcCoverageChartComponent } from '../sdc-coverage-chart.component';

describe('SdcCoverageChartComponent', () => {
  let component: SdcCoverageChartComponent;
  let fixture: ComponentFixture<SdcCoverageChartComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [SdcCoverageChartComponent, CommonModule, NgxEchartsModule.forRoot({ echarts: () => import('echarts') })],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcCoverageChartComponent);
    component = fixture.componentInstance;
    component.coverage = { id: 1, name: '', coverage: 34 };
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });
});
