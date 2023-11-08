import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DataInfo, GenericDataInfo } from 'src/app/core/models';
import { ValueType } from 'src/app/core/models/sdc';
import { ChartConfig } from '../../sdc-charts';
import { SdcTimeEvolutionMultichartComponent } from '../sdc-time-evolution-multichart.component';

describe('SdcTimeEvolutionMultichartComponent', () => {
  let component: SdcTimeEvolutionMultichartComponent;
  let fixture: ComponentFixture<SdcTimeEvolutionMultichartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
      declarations: [SdcTimeEvolutionMultichartComponent]
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
    const graphData: GenericDataInfo<string[]> = { axis1: ['11', '21'], axis2: ['21', '22'] };
    component.data = {
      graph: [
        { axis: '', data: 'axis1=11;axis2=21' },
        { axis: '', data: 'axis1=21;axis2=22' }
      ],
      type: ValueType.NUMERIC
    };

    expect(component['graphData']).toEqual(graphData);
  });

  it('should convert string graph data to DataInfo', () => {
    const stringGraphData = 'axis1=11;axis2=21';
    const dataInfo = {
      axis1: '11',
      axis2: '21'
    };

    expect(component['stringGraphToDataInfo'](stringGraphData)).toEqual(dataInfo);
  });

  it('should group DataInfo', () => {
    const dataInfo: DataInfo[] = [
      {
        axis1: '11',
        axis2: '21'
      },
      {
        axis1: '21',
        axis2: '22'
      }
    ];
    const groupedDataInfo: GenericDataInfo<string[]> = { axis1: ['11', '21'], axis2: ['21', '22'] };

    expect(component['groupDataInfo'](dataInfo)).toEqual(groupedDataInfo);
  });
});
