import { ValueType } from 'src/app/core/models/sdc';

export interface ChartConfig {
  axis: AxiValues;
  data: ChartData[];
  type?: ValueType;
}

export interface AxiValues {
  xAxis?: string[];
  yAxis?: string[];
}

export interface ChartData {
  name?: string;
  values: ChartValue | ChartValue[];
}

export interface ChartValue {
  color?: string;
  value: string;
}
