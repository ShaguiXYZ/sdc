import { MetricStates, MetricState } from 'src/app/shared/lib';

export interface SdcCoverageRange {
  min: number;
  max: number;
}

export const SdcApplicationsCoverage: {
  [key: string]: SdcCoverageRange;
} = {
  [MetricStates.CRITICAL]: {
    min: -1, // to include 0 as minimal
    max: MetricState[MetricStates.CRITICAL].value
  },
  [MetricStates.WITH_RISK]: {
    min: MetricState[MetricStates.CRITICAL].value,
    max: MetricState[MetricStates.WITH_RISK].value
  },
  [MetricStates.ACCEPTABLE]: {
    min: MetricState[MetricStates.WITH_RISK].value,
    max: MetricState[MetricStates.ACCEPTABLE].value
  },
  [MetricStates.PERFECT]: {
    min: MetricState[MetricStates.ACCEPTABLE].value,
    max: MetricState[MetricStates.PERFECT].value
  }
};
