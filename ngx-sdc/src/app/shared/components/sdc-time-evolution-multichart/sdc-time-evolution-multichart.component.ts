import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { DataInfo } from 'src/app/core/models';
import { ChartConfig, SdcGraphData } from '../../models';
import { SdcTimeEvolutionChartComponent, stringGraphToRecord } from '../sdc-charts';

@Component({
  selector: 'sdc-time-evolution-multichart',
  templateUrl: './sdc-time-evolution-multichart.component.html',
  styleUrls: ['./sdc-time-evolution-multichart.component.scss'],
  standalone: true,
  imports: [SdcTimeEvolutionChartComponent, CommonModule]
})
export class SdcTimeEvolutionMultichartComponent {
  public metricChartConfig: ChartConfig = { axis: {}, data: [] };

  @Input()
  public size: { height?: number; width?: number } = {};

  @Input()
  public set data(value: SdcGraphData) {
    this.metricChartConfig = this.toChartconfig(value);
  }

  private graphData: DataInfo<number[]> = {};

  private toChartconfig(value: SdcGraphData): ChartConfig {
    const data: string[] = value.graph.map(v => v.data);
    this.graphData = this.groupDataInfo(data.map(stringGraphToRecord));

    return {
      axis: { xAxis: value.graph.map(v => v.axis) },
      data: Object.entries(this.graphData).map(([name, value]) => ({
        name,
        smooth: true,
        values: value.map(graphValue => ({
          value: graphValue
        }))
      })),
      options: { showVisualMap: false, legendPosition: value.legendPosition },
      type: value.type
    };
  }

  private groupDataInfo = (data: DataInfo<any>[]): DataInfo<any[]> => {
    let graphIndex = 0;

    return data.reduce((previous, current) => {
      Object.keys(current).forEach(key => {
        if (previous[key]) {
          const actualLenght = previous[key].length;
          previous[key].push(...Array(graphIndex - actualLenght).fill(0));
          previous[key].push(current[key]);
        } else {
          const graphData = Array(graphIndex).fill(0);
          graphData.push(current[key]);
          previous[key] = graphData;
        }
      });

      graphIndex++;

      return previous;
    }, {});
  };
}
