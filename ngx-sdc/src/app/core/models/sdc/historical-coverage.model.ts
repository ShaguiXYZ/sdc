/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
import { IPageable } from './pageable.model';

export interface ITimeCoverage {
  coverage: number;
  analysisDate: number;
}

export interface IHistoricalCoverage<T> {
  data: T;
  historical: IPageable<ITimeCoverage>;
}
