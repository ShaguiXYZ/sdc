import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SdcPieChartComponent } from '../sdc-pie-chart.component';

import { NGX_ECHARTS_CONFIG } from 'ngx-echarts';

describe('SdcPieChartComponent', () => {
  let component: SdcPieChartComponent;
  let fixture: ComponentFixture<SdcPieChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SdcPieChartComponent],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [{ provide: NGX_ECHARTS_CONFIG, useValue: {} }]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcPieChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have styleSize property', () => {
    expect(component.styleSize).toBeDefined();
  });

  it('should have styleSize property with height and width', () => {
    expect(component.styleSize).toEqual({ 'height.px': component.size, 'width.px': component.size });
  });
});
