import { ValueType } from 'src/app/core/models/sdc';

export interface ChartValue {
  xAxis: string;
  data: string;
  color?: string;
  type?: ValueType;
}

export interface ChartConfig {
  name?: string;
  values: ChartValue[];
}
