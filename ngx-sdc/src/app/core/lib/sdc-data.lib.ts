import { DataInfo, GenericDataInfo } from '../interfaces/dataInfo';
import { ICoverageModel } from '../models/sdc';

export const sortCoverageData = (a: Omit<ICoverageModel, 'id'>, b: Omit<ICoverageModel, 'id'>): number =>
  // 101 because if the coverage is not defined (maximum coverage value is 100)
  // it means that the component has not been analyzed and we want to place it at the end
  (a.coverage ?? 101) - (b.coverage ?? 101) || a.name.localeCompare(b.name);

export const stringGraphToDataInfo = (data: string): DataInfo => {
  const dataInfo: DataInfo = {};

  data
    .split(';')
    // .filter(/(\w+)=(\d+)(.?(\d+))?/.test)
    .forEach(value => {
      const info = value.split('=');
      dataInfo[info[0]] = info[1];
    });

  return dataInfo;
};

export const groupDataInfo = (data: DataInfo[]): GenericDataInfo<string[]> => {
  const group: GenericDataInfo<string[]> = {};

  let graphIndex = 0;
  return data.reduce((previous, current) => {
    Object.keys(current).forEach(key => {
      if (previous[key]) {
        const actualLenght = previous[key].length;
        previous[key].push(...Array(graphIndex - actualLenght).fill(0));
        previous[key].push(current[key]);
      } else {
        const graphData = Array(graphIndex).fill(0);
        graphData.push(current[key]);
        previous[key] = graphData;
      }
    });
    graphIndex++;

    return previous;
  }, {});
};
