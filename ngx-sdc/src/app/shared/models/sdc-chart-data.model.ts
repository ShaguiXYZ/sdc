import { ValueType } from 'src/app/core/models/sdc';
import { LegendPosition } from '../components/sdc-charts';

export interface SdcChartData {
  graph: {
    axis: string;
    data: string;
  }[];
  legendPosition?: LegendPosition;
  type: ValueType;
}
