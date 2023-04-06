import { AvailableMetricStates, MetricState } from 'src/app/core/lib';

export interface SdcCoverageRange {
  min: number;
  max: number;
}

export const SdcApplicationsCoverage: {
  [key: string]: SdcCoverageRange;
} = {
  [AvailableMetricStates.CRITICAL]: {
    min: -1, // to include 0 as minimal
    max: MetricState[AvailableMetricStates.CRITICAL].value
  },
  [AvailableMetricStates.WITH_RISK]: {
    min: MetricState[AvailableMetricStates.CRITICAL].value,
    max: MetricState[AvailableMetricStates.WITH_RISK].value
  },
  [AvailableMetricStates.ACCEPTABLE]: {
    min: MetricState[AvailableMetricStates.WITH_RISK].value,
    max: MetricState[AvailableMetricStates.ACCEPTABLE].value
  },
  [AvailableMetricStates.PERFECT]: {
    min: MetricState[AvailableMetricStates.ACCEPTABLE].value,
    max: MetricState[AvailableMetricStates.PERFECT].value
  }
};
