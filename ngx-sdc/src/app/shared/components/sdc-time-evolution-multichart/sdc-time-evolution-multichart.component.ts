import { Component, Input } from '@angular/core';
import { DataInfo, GenericDataInfo } from 'src/app/core/interfaces/dataInfo';
import { ChartConfig, ChartData, SdcGraphData } from '../../models';

@Component({
  selector: 'sdc-time-evolution-multichart',
  templateUrl: './sdc-time-evolution-multichart.component.html',
  styleUrls: ['./sdc-time-evolution-multichart.component.scss']
})
export class SdcTimeEvolutionMultichartComponent {
  public metricChartConfig: ChartConfig = { axis: {}, data: [] };

  private graphData: GenericDataInfo<string[]> = {};

  @Input()
  public set data(value: SdcGraphData) {
    this.metricChartConfig = this.toChartconfig(value);
  }

  private toChartconfig(value: SdcGraphData): ChartConfig {
    const data: string[] = value.graph.map(v => v.data);
    this.graphData = this.groupDataInfo(data.map(this.stringGraphToDataInfo));

    return {
      axis: { xAxis: value.graph.map(v => v.axis) },
      data: Object.keys(this.graphData).map(
        (key): ChartData => ({
          name: key,
          smooth: true,
          values: this.graphData[key].map(graphValue => ({
            value: graphValue
          }))
        })
      ),
      options: { showVisualMap: false, legendPosition: value.legendPosition },
      type: value.type
    };
  }

  private stringGraphToDataInfo = (data: string): DataInfo => {
    const dataInfo: DataInfo = {};

    data
      .split(';')
      .filter(value => /(\w+)=(\d+)(.?(\d+))?/.test(value))
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
