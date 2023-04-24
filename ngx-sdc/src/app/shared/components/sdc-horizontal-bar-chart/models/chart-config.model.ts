export interface ChartValue {
  yAxis: string;
  data: string;
  color?: string;
}

export interface ChartConfig {
  values: ChartValue[];
}
