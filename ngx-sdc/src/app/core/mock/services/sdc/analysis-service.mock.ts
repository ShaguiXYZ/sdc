import { IMetricAnalysisModel, IPageable } from 'src/app/core/models/sdc';

export class AnalysisServiceMock {
  analysis() {
    return Promise.resolve({ analysisDate: 1, coverage: 1, metric: {}, analysisValues: {} });
  }

  metricHistory() {
    return Promise.resolve({
      paging: { pageIndex: 0, pageSize: 1, pages: 1, elements: 1 },
      page: [{ analysisDate: 1, coverage: 1, metric: {}, analysisValues: {} }]
    });
  }
}
