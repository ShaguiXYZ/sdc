import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { DataInfo } from '@shagui/ng-shagui/core';
import { SdcChartData } from '../../models';
import { ChartConfig, ChartSize, SdcTimeEvolutionChartComponent, stringGraphToRecord } from '../sdc-charts';

@Component({
  selector: 'sdc-time-evolution-multichart',
  template: `<sdc-time-evolution-chart [config]="metricChartConfig" [size]="size" />`,
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone: true,
  imports: [SdcTimeEvolutionChartComponent, CommonModule]
})
export class SdcTimeEvolutionMultichartComponent {
  @Input()
  public size: ChartSize = {};

  public metricChartConfig: ChartConfig = { axis: {}, data: [] };

  private graphData: DataInfo<number[]> = {};

  @Input()
  public set data(value: SdcChartData) {
    this.metricChartConfig = this.toChartconfig(value);
  }

  private toChartconfig(value: SdcChartData): ChartConfig {
    const data: string[] = value.graph.map(v => v.data);
    this.graphData = this.groupDataInfo(data.map(stringGraphToRecord));

    return {
      axis: { xAxis: value.graph.map(v => v.axis) },
      data: Object.entries(this.graphData).map(([name, values]) => ({
        name,
        smooth: true,
        values: values.map(graphValue => ({
          value: graphValue
        }))
      })),
      options: { showVisualMap: false, legendPosition: value.legendPosition },
      type: value.type
    };
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  private groupDataInfo = (data: DataInfo<any>[]): DataInfo<number[]> => {
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
