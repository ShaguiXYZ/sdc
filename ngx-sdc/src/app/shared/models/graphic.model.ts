import { ValueType } from 'src/app/core/models/sdc';

export type LegendPosition = 'bottom' | 'left' | 'top' | 'right';

export interface SdcGraphData {
  graph: {
    axis: string;
    data: string;
  }[];
  legendPosition?: LegendPosition;
  type: ValueType;
}

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
  value: number | string;
}

export interface ChartOptions {
  legendPosition?: LegendPosition;
  showVisualMap?: boolean;
}
