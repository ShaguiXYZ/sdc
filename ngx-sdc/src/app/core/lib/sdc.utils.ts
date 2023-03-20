import { IComponentModel } from '../models/sdc';

export const componentsCoverage = (components: IComponentModel[]): number => {
  const coverageComponents = components.filter(component => component.coverage);

  if (coverageComponents.length > 0) {
    const total = coverageComponents.map(component => component.coverage).reduce((acc = 0, coverage = 0) => (acc += coverage), 0);
    return (total || 0) / coverageComponents.length;
  }

  return 0;
};
