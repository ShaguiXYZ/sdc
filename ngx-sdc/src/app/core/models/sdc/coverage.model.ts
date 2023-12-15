export interface ICoverageModel {
  id: number;
  name: string;
  coverage?: number;
  trend?: number;
  blocked?: boolean;
}

export namespace ICoverageModel {
  export const expectedCoverage = (a: Omit<ICoverageModel, 'id'>): number | undefined =>
    a.coverage ? a.coverage + (a.trend ?? 0) : a.trend;

  export const sort = (a: Omit<ICoverageModel, 'id'>, b: Omit<ICoverageModel, 'id'>): number =>
    // 101 because if the coverage is not defined (maximum coverage value is 100)
    // it means that the component has not been analyzed and we want to place it at the end
    (a.coverage ?? 101) - (b.coverage ?? 101) || a.name.localeCompare(b.name);

  export const sortExpected = (a: Omit<ICoverageModel, 'id'>, b: Omit<ICoverageModel, 'id'>): number =>
    // 101 because if the coverage is not defined (maximum coverage value is 100)
    // it means that the component has not been analyzed and we want to place it at the end
    (ICoverageModel.expectedCoverage(a) ?? 101) - (ICoverageModel.expectedCoverage(b) ?? 101) || a.name.localeCompare(b.name);
}
