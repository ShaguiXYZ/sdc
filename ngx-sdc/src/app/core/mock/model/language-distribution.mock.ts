import { AnalysisType, IMetricAnalysisModel, IPageable, ValueType } from '../../models/sdc';

export const languageDistributionMock: IPageable<IMetricAnalysisModel> = {
  paging: {
    pageIndex: 0,
    pageSize: 10,
    pages: 1,
    elements: 2
  },
  page: [
    {
      analysisDate: 1698674551684,
      coverage: 0,
      metric: {
        id: 31,
        name: 'Language Distribution',
        type: AnalysisType.GIT,
        valueType: ValueType.NUMERIC_MAP
      },
      name: 'Language Distribution',
      analysisValues: {
        metricValue: 'TypeScript=285992;Java=432008;SCSS=11628;JavaScript=2048;HTML=27013'
      }
    },
    {
      analysisDate: 1698674551684,
      coverage: 0,
      metric: {
        id: 31,
        name: 'Language Distribution',
        type: AnalysisType.GIT,
        valueType: ValueType.NUMERIC_MAP
      },
      name: 'Language Distribution',
      analysisValues: {
        metricValue: 'TypeScript=285992;Java=432008;SCSS=11628;JavaScript=2048;HTML=27013'
      }
    }
  ]
};
