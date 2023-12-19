export interface IAnalysisConfig {
  precision: number;
  trendDeep: number;
  trendHeat: number;
}

export interface IJpaConfig {
  elementsByPage: number;
}

export interface IAppConfiguration {
  analysis: IAnalysisConfig;
  jpa: IJpaConfig;
  title: string;
}
