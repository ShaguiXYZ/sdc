import { Component, Input } from '@angular/core';
import { GenericDataInfo } from 'src/app/core/interfaces/dataInfo';
import { groupDataInfo, stringGraphToDataInfo } from 'src/app/core/lib';
import { ValueType } from 'src/app/core/models/sdc';
import { ChartConfig } from '../../models';

@Component({
  selector: 'sdc-time-evolution-multichart',
  templateUrl: './sdc-time-evolution-multichart.component.html',
  styleUrls: ['./sdc-time-evolution-multichart.component.scss']
})
export class SdcTimeEvolutionMultichartComponent {
  public metricChartConfig!: ChartConfig;

  private graphData: GenericDataInfo<string[]> = {};

  @Input()
  set data(value: { graph: { axis: string; data: string }[]; type?: ValueType }) {
    const data: string[] = value.graph.map(v => v.data);
    this.graphData = groupDataInfo(data.map(stringGraphToDataInfo));

    this.metricChartConfig = {
      axis: { xAxis: value.graph.map(v => v.axis) },
      data: Object.keys(this.graphData).map(key => ({
        name: key,
        values: this.graphData[key].map(value => ({
          value
        }))
      })),
      type: value.type
    };
  }
}
