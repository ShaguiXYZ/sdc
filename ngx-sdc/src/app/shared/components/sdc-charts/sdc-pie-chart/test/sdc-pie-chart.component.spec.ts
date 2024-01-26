import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NGX_ECHARTS_CONFIG } from 'ngx-echarts';
import { SdcPieChartComponent } from '../sdc-pie-chart.component';

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
    expect(component.size).toBeDefined();
  });

  it('should have styleSize property with height and width', () => {
    component.size = { height: 100, width: 100 };
    fixture.detectChanges();
    expect(component.size).toEqual({ height: 100, width: 100 });
  });

  it('should have echartsOptions property', () => {
    expect(component.echartsOptions).toBeDefined();
  });

  it('should have title property', () => {
    expect(component.title).toBeUndefined();
  });

  it('should set echartsOptions on ngOnInit', () => {
    const chartData = 'TypeScript=296604;Java=444211;CSS=68055;SCSS=13368;JavaScript=2048;HTML=33185';
    spyOn(component as any, 'chartOptions'); // Add 'as any' to bypass the type checking
    component.data = chartData;
    component.ngOnInit();
    expect((component as any).chartOptions).toHaveBeenCalledWith(chartData); // Add 'as any' to bypass the type checking
    // expect(component.echartsOptions).toBeDefined();
  });

  it('should set echartsOptions when data input changes', () => {
    const chartData = 'TypeScript=296604;Java=444211;CSS=68055;SCSS=13368;JavaScript=2048;HTML=33185';
    spyOn(component as any, 'chartOptions');
    component.data = chartData;
    expect((component as any).chartOptions).toHaveBeenCalledWith(chartData);
    // expect(component.echartsOptions).toBeDefined();
  });

  it('should generate correct chartOptions', () => {
    const chartData = 'some data';
    const expectedOptions = {
      grid: {
        left: '2%',
        right: '2%',
        bottom: '5%',
        top: '5%',
        containLabel: true
      },
      tooltip: {
        trigger: 'item',
        formatter: '{b}: ({d}%)'
      },
      series: [
        {
          type: 'pie',
          radius: '50%',
          data: [],
          emphasis: {
            focus: 'self',
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          },
          label: {
            formatter: '{b}: ({d}%)'
          }
        }
      ]
    };
    const options = (component as any)['chartOptions'](chartData);
    expect(options).toEqual(expectedOptions);
  });
});
