import { ICoverageModel } from './coverage.model';

export * from './analysis-type.model';
export * from './analysis-values.model';
export * from './component.model';
export * from './coverage.model';
export * from './department.model';
export * from './metric-analysis.model';
export * from './metric.model';
export * from './pageable.model';
export * from './squad.model';

export const sortCoverageData = (a: Omit<ICoverageModel, 'id'>, b: Omit<ICoverageModel, 'id'>): number =>
  // 101 because if the coverage is not defined (maximum coverage value is 100)
  // it means that the component has not been analyzed and we want to place it at the end
  (a.coverage ?? 101) - (b.coverage ?? 101) || a.name.localeCompare(b.name);
