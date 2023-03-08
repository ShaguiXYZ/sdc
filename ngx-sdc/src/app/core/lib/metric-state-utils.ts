import { COLOR_PREFIX } from 'src/app/shared/config/app.constants';
import { GenericDataInfo } from '../../shared/interfaces/dataInfo';

export interface MetricConfig {
  value: number;
  style: string;
}

export enum AvailableMetricStates {
  CRITICAL,
  WITH_RISK,
  ACCEPTABLE,
  PERFECT
}

export const MetricState: GenericDataInfo<MetricConfig> = {
  [AvailableMetricStates.CRITICAL]: { value: 50, style: 'critical' },
  [AvailableMetricStates.WITH_RISK]: { value: 75, style: 'with_risk' },
  [AvailableMetricStates.ACCEPTABLE]: { value: 99, style: 'acceptable' },
  [AvailableMetricStates.PERFECT]: { value: 100, style: 'perfect' }
};

export const styleByCoverage = (coverage: number): string => {
  let _class = MetricState[AvailableMetricStates.PERFECT].style;
  if (coverage < MetricState[AvailableMetricStates.CRITICAL].value) {
    _class = MetricState[AvailableMetricStates.CRITICAL].style;
  } else if (coverage < MetricState[AvailableMetricStates.WITH_RISK].value) {
    _class = MetricState[AvailableMetricStates.WITH_RISK].style;
  } else if (coverage < MetricState[AvailableMetricStates.ACCEPTABLE].value) {
    _class = MetricState[AvailableMetricStates.ACCEPTABLE].style;
  }

  return `${COLOR_PREFIX}${_class}`;
};
