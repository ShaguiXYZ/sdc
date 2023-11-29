import { MetricStates } from '../lib';

export interface ApplicationsFilter {
  coverage?: MetricStates;
  squad?: number;
  name?: string;
  tags?: string[];
}

export interface ApplicationsContextData {
  filter?: ApplicationsFilter;
  page: number;
}
