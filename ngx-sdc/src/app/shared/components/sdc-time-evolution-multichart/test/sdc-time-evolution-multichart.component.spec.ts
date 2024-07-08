import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DataInfo } from '@shagui/ng-shagui/core';
import { NgxEchartsModule } from 'ngx-echarts';
import { ValueType } from 'src/app/core/models/sdc';
import { SdcChartData } from 'src/app/shared/models';
import { ChartConfig } from '../../sdc-charts';
import { SdcTimeEvolutionMultichartComponent } from '../sdc-time-evolution-multichart.component';

describe('SdcTimeEvolutionMultichartComponent', () => {
  let component: SdcTimeEvolutionMultichartComponent;
  let fixture: ComponentFixture<SdcTimeEvolutionMultichartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        SdcTimeEvolutionMultichartComponent,
        NgxEchartsModule.forRoot({
          echarts: () => import('echarts')
        })
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcTimeEvolutionMultichartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set metricChartConfig', () => {
    const chartConfig: ChartConfig = { axis: {}, data: [] };
    component.metricChartConfig = chartConfig;
    expect(component.metricChartConfig).toEqual(chartConfig);
  });

  it('should set graphData', () => {
    const graphData: DataInfo<number[]> = { axis1: [11, 21], axis2: [21, 22] };
    component.data = {
      graph: [
        { axis: '', data: 'axis1=11;axis2=21' },
        { axis: '', data: 'axis1=21;axis2=22' }
      ],
      type: ValueType.NUMERIC
    };

    expect(component['graphData']).toEqual(graphData);
  });

  it('should convert string graph data to chart config', () => {
    const stringGraphData: SdcChartData = { graph: [{ axis: 'graph-axis', data: 'axis1=11;axis2=21' }], type: ValueType.NUMERIC };
    const dataInfo: ChartConfig = {
      axis: { xAxis: ['graph-axis'] },
      data: [
        { name: 'axis1', smooth: true, values: [{ value: 11 }] },
        { name: 'axis2', smooth: true, values: [{ value: 21 }] }
      ],
      options: { legendPosition: undefined, showVisualMap: false },
      type: ValueType.NUMERIC
    };

    expect(component['toChartconfig'](stringGraphData)).toEqual(dataInfo);
  });

  it('should group DataInfo', () => {
    const dataInfo: DataInfo<any>[] = [
      {
        axis1: 11,
        axis2: 21
      },
      {
        axis1: 21,
        axis2: 22
      }
    ];
    const groupedDataInfo: DataInfo<number[]> = { axis1: [11, 21], axis2: [21, 22] };

    expect(component['groupDataInfo'](dataInfo)).toEqual(groupedDataInfo);
  });
});
