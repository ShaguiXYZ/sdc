const COLOR_PREFIX = 'color--';

export enum StateColors {
  CRITICAL = '#EE4266',
  RISK = '#EFBE25',
  ACCEPTABLE = '#1E8927',
  PERFECT = '#006192'
}

export enum AvailableMetricStates {
  PERFECT,
  ACCEPTABLE,
  WITH_RISK,
  CRITICAL
}

const DEFAULT_METRIC_STATE = AvailableMetricStates.CRITICAL;

export interface MetricConfig {
  value: number;
  style: string;
}

export const MetricState: { [key: string]: MetricConfig } = {
  [AvailableMetricStates.CRITICAL]: { value: 50, style: 'critical' },
  [AvailableMetricStates.WITH_RISK]: { value: 75, style: 'with_risk' },
  [AvailableMetricStates.ACCEPTABLE]: { value: 99, style: 'acceptable' },
  [AvailableMetricStates.PERFECT]: { value: 100, style: 'perfect' }
};

export const stateByCoverage = (coverage: number): AvailableMetricStates => {
  let _class = DEFAULT_METRIC_STATE;

  if (coverage <= MetricState[AvailableMetricStates.CRITICAL].value) {
    _class = AvailableMetricStates.CRITICAL;
  } else if (coverage <= MetricState[AvailableMetricStates.WITH_RISK].value) {
    _class = AvailableMetricStates.WITH_RISK;
  } else if (coverage <= MetricState[AvailableMetricStates.ACCEPTABLE].value) {
    _class = AvailableMetricStates.ACCEPTABLE;
  } else if (coverage <= MetricState[AvailableMetricStates.PERFECT].value) {
    _class = AvailableMetricStates.PERFECT;
  }

  return _class;
};

export const styleByName = (name: string): string => `${COLOR_PREFIX}${name}`;
export const styleByMetricState = (state: AvailableMetricStates): string => styleByName(MetricState[state].style);
export const styleByCoverage = (coverage: number): string => styleByMetricState(stateByCoverage(coverage));
