import { ValueType } from 'src/app/core/models/sdc';

export interface ChartConfig {
  axis: AxiValues;
  data: ChartData[];
  options?: ChartOptions;
  type?: ValueType;
}

export interface AxiValues {
  xAxis?: string[];
  yAxis?: string[];
}

export interface ChartData {
  name?: string;
  lineStyle?: 'solid' | 'dashed' | 'dotted';
  smooth?: boolean;
  values: ChartValue | ChartValue[];
}

export interface ChartValue {
  color?: string;
  value: string;
}

export interface ChartOptions {
  showVisualMap?: boolean;
}
