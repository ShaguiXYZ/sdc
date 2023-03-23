import { COLOR_PREFIX } from 'src/app/core/constants/app.constants';

export enum StateColors {
  CRITICAL = '#EE4266',
  RISK = '#EFBE25',
  ACCEPTABLE = '#1E8927',
  PERFECT = '#F2EFF5'
}

export enum AvailableMetricStates {
  CRITICAL,
  WITH_RISK,
  ACCEPTABLE,
  PERFECT
}

const DEFAULT_METRIC_STATE = AvailableMetricStates.CRITICAL;

export interface MetricConfig {
  value: number;
  style: string;
}

export const MetricState: { [key in AvailableMetricStates]: MetricConfig } = {
  [AvailableMetricStates.CRITICAL]: { value: 50, style: 'critical' },
  [AvailableMetricStates.WITH_RISK]: { value: 75, style: 'with_risk' },
  [AvailableMetricStates.ACCEPTABLE]: { value: 99, style: 'acceptable' },
  [AvailableMetricStates.PERFECT]: { value: 100, style: 'perfect' }
};

export const stateByCoverage = (coverage: number): AvailableMetricStates => {
  let _class = DEFAULT_METRIC_STATE;

  if (coverage < MetricState[AvailableMetricStates.CRITICAL].value) {
    _class = AvailableMetricStates.CRITICAL;
  } else if (coverage < MetricState[AvailableMetricStates.WITH_RISK].value) {
    _class = AvailableMetricStates.WITH_RISK;
  } else if (coverage < MetricState[AvailableMetricStates.ACCEPTABLE].value) {
    _class = AvailableMetricStates.ACCEPTABLE;
  }

  return _class;
};

export const styleByMetricState = (state: AvailableMetricStates): string => `${COLOR_PREFIX}${MetricState[state].style}`;
export const styleByCoverage = (coverage: number): string => styleByMetricState(stateByCoverage(coverage));
