import { MetricStates } from '../lib';

export interface ApplicationsFilter {
  metricState?: MetricStates;
  name?: string;
  squad?: number;
  tags?: string[];
}

export interface ApplicationsContextData {
  filter?: ApplicationsFilter;
  page: number;
}
