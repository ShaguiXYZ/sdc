import { hasValue } from '../../core/lib/object-utils.lib';
import { StateColors } from '../constants';
import { SdcRange } from '../models';

const COLOR_PREFIX = 'color--';

export enum MetricStates {
  PERFECT = 'PERFECT',
  ACCEPTABLE = 'ACCEPTABLE',
  WITH_RISK = 'WITH_RISK',
  CRITICAL = 'CRITICAL'
}

export const DEFAULT_METRIC_STATE = MetricStates.ACCEPTABLE;

export interface MetricConfig {
  value: number;
  style: string;
  color: StateColors;
}

export const MetricState: Record<MetricStates, MetricConfig> = {
  [MetricStates.CRITICAL]: { value: 50, style: 'critical', color: StateColors.CRITICAL },
  [MetricStates.WITH_RISK]: { value: 75, style: 'with_risk', color: StateColors.RISK },
  [MetricStates.ACCEPTABLE]: { value: 95, style: 'acceptable', color: StateColors.ACCEPTABLE },
  [MetricStates.PERFECT]: { value: 100, style: 'perfect', color: StateColors.PERFECT }
};

export const stateByCoverage = (coverage: number): MetricStates => {
  let _class = DEFAULT_METRIC_STATE;

  if (hasValue(coverage)) {
    if (coverage < MetricState[MetricStates.CRITICAL].value) {
      _class = MetricStates.CRITICAL;
    } else if (coverage < MetricState[MetricStates.WITH_RISK].value) {
      _class = MetricStates.WITH_RISK;
    } else if (coverage < MetricState[MetricStates.ACCEPTABLE].value) {
      _class = MetricStates.ACCEPTABLE;
    } else if (coverage >= MetricState[MetricStates.ACCEPTABLE].value) {
      _class = MetricStates.PERFECT;
    }
  }

  return _class;
};

export const styleByName = (name: string): string => `${COLOR_PREFIX}${name}`;
export const styleByMetricState = (state: MetricStates): string => styleByName(MetricState[state].style);
export const styleByCoverage = (coverage: number): string => styleByMetricState(stateByCoverage(coverage));

export const rangeByState = (state: MetricStates): SdcRange =>
  ({
    [MetricStates.CRITICAL]: { min: 0, max: MetricState[MetricStates.CRITICAL].value },
    [MetricStates.WITH_RISK]: {
      min: MetricState[MetricStates.CRITICAL].value,
      max: MetricState[MetricStates.WITH_RISK].value
    },
    [MetricStates.ACCEPTABLE]: {
      min: MetricState[MetricStates.WITH_RISK].value,
      max: MetricState[MetricStates.ACCEPTABLE].value
    },
    [MetricStates.PERFECT]: { min: MetricState[MetricStates.ACCEPTABLE].value, max: 101 }
  }[state]);
