import { hasValue } from '../../core/lib/object-utils.lib';
import { StateColors, COLOR_PREFIX } from '../constants';

export enum AvailableMetricStates {
  PERFECT,
  ACCEPTABLE,
  WITH_RISK,
  CRITICAL
}

export const DEFAULT_METRIC_STATE = AvailableMetricStates.ACCEPTABLE;

export interface MetricConfig {
  value: number;
  style: string;
  color: StateColors;
}

export const MetricState: { [key: string]: MetricConfig } = {
  [AvailableMetricStates.CRITICAL]: { value: 50, style: 'critical', color: StateColors.CRITICAL },
  [AvailableMetricStates.WITH_RISK]: { value: 75, style: 'with_risk', color: StateColors.RISK },
  [AvailableMetricStates.ACCEPTABLE]: { value: 95, style: 'acceptable', color: StateColors.ACCEPTABLE },
  [AvailableMetricStates.PERFECT]: { value: 100, style: 'perfect', color: StateColors.PERFECT }
};

export const stateByCoverage = (coverage: number): AvailableMetricStates => {
  let _class = DEFAULT_METRIC_STATE;

  if (hasValue(coverage)) {
    if (coverage < MetricState[AvailableMetricStates.CRITICAL].value) {
      _class = AvailableMetricStates.CRITICAL;
    } else if (coverage < MetricState[AvailableMetricStates.WITH_RISK].value) {
      _class = AvailableMetricStates.WITH_RISK;
    } else if (coverage < MetricState[AvailableMetricStates.ACCEPTABLE].value) {
      _class = AvailableMetricStates.ACCEPTABLE;
    } else if (coverage <= MetricState[AvailableMetricStates.PERFECT].value) {
      _class = AvailableMetricStates.PERFECT;
    }
  }

  return _class;
};

export const styleByName = (name: string): string => `${COLOR_PREFIX}${name}`;
export const styleByMetricState = (state: AvailableMetricStates): string => styleByName(MetricState[state].style);
export const styleByCoverage = (coverage: number): string => styleByMetricState(stateByCoverage(coverage));

export const rangeByState = (state: AvailableMetricStates): { min: number; max: number } =>
  ({
    [AvailableMetricStates.CRITICAL]: { min: 0, max: MetricState[AvailableMetricStates.CRITICAL].value },
    [AvailableMetricStates.WITH_RISK]: {
      min: MetricState[AvailableMetricStates.CRITICAL].value,
      max: MetricState[AvailableMetricStates.WITH_RISK].value
    },
    [AvailableMetricStates.ACCEPTABLE]: {
      min: MetricState[AvailableMetricStates.WITH_RISK].value,
      max: MetricState[AvailableMetricStates.ACCEPTABLE].value
    },
    [AvailableMetricStates.PERFECT]: { min: MetricState[AvailableMetricStates.ACCEPTABLE].value, max: 100 }
  }[state]);
