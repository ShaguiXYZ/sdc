import { UriType } from './uri.model';

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
  })[type] ?? 'fa-solid fa-chart-line';

export const uriTypeByAnalysisType = (type: AnalysisType): UriType =>
  ({
    [AnalysisType.DEPENDABOT]: UriType.GIT,
    [AnalysisType.GIT]: UriType.GIT,
    [AnalysisType.GIT_XML]: UriType.GIT,
    [AnalysisType.GIT_JSON]: UriType.GIT,
    [AnalysisType.SONAR]: UriType.SONAR
  })[type];
