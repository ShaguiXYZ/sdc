export enum AnalysisType {
  DEPENDABOT = 'DEPENDABOT',
  GIT = 'GIT',
  GIT_XML = 'GIT_XML',
  GIT_JSON = 'GIT_JSON',
  SONAR = 'SONAR'
}

export const iconByType = (type: AnalysisType): string =>
  ({
    [AnalysisType.DEPENDABOT]: 'fa-brands fa-github',
    [AnalysisType.GIT]: 'fa-brands fa-github',
    [AnalysisType.GIT_XML]: 'fa-brands fa-github',
    [AnalysisType.GIT_JSON]: 'fa-brands fa-github',
    [AnalysisType.SONAR]: 'fa-solid fa-satellite-dish'
  }[type] ?? 'fa-solid fa-chart-line');
