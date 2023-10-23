import { Component, Input } from '@angular/core';
import { DataInfo, GenericDataInfo } from 'src/app/core/interfaces/dataInfo';
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
    this.graphData = this.groupDataInfo(data.map(this.stringGraphToDataInfo));

    this.metricChartConfig = {
      axis: { xAxis: value.graph.map(v => v.axis) },
      data: Object.keys(this.graphData).map(key => ({
        name: key,
        smooth: true,
        values: this.graphData[key].map(graphValue => ({
          value: graphValue
        }))
      })),
      options: { showVisualMap: false },
      type: value.type
    };
  }

  private stringGraphToDataInfo = (data: string): DataInfo => {
    const dataInfo: DataInfo = {};

    data
      .split(';')
      // .filter(/(\w+)=(\d+)(.?(\d+))?/.test)
      .forEach(eq => {
        const [key, ...value] = eq.split('=');
        dataInfo[key] = value.join('=');
      });

    return dataInfo;
  };

  private groupDataInfo = (data: DataInfo[]): GenericDataInfo<string[]> => {
    const group: GenericDataInfo<string[]> = {};

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
