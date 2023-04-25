import { ICoverageModel } from '../models/sdc';

export const componentsCoverage = (coverages: ICoverageModel[]): number => {
  const coverageComponents = coverages.filter(data => data.coverage);

  if (coverageComponents.length > 0) {
    const total = coverageComponents.map(component => component.coverage).reduce((acc = 0, coverage = 0) => (acc += coverage), 0);
    return (total || 0) / coverageComponents.length;
  }

  return 0;
};
