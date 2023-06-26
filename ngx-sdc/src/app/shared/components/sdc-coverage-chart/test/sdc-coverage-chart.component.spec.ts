import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { emptyFn } from 'src/app/core/lib';
import { SdcCoverageChartComponent } from '../sdc-coverage-chart.component';
import { CommonModule } from '@angular/common';
import { EchartsxModule } from 'echarts-for-angular';

describe('SdcCoverageChartComponent', () => {
  let component: SdcCoverageChartComponent;
  let fixture: ComponentFixture<SdcCoverageChartComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SdcCoverageChartComponent],
      imports: [CommonModule, EchartsxModule],
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
