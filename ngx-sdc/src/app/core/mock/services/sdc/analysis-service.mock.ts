export class AnalysisServiceMock {
  analysis() {
    return Promise.resolve({ analysisDate: 1, coverage: 1, metric: {}, analysisValues: {} });
  }
}
