import { version } from 'package.json';

export interface IAnalysisConfig {
  precision: number;
  trendDeep: number;
  trendHeat: number;
}

export interface IJpaConfig {
  elementsByPage: number;
}

export interface IAppConfigurationDTO {
  bffVersion: string;
  rwsVersion: string;
  analysis: IAnalysisConfig;
  jpa: IJpaConfig;
  title: string;
}

export interface IAppConfigurationModel {
  version: {
    bffVersion: string;
    ngxVersion: string;
    rwsVersion: string;
  };
  analysis: IAnalysisConfig;
  jpa: IJpaConfig;
  title: string;
}

export namespace IAppConfigurationModel {
  export const fromDTO = (dto: IAppConfigurationDTO): IAppConfigurationModel =>
    new AppConfigurationModel(
      { bffVersion: dto.bffVersion, ngxVersion: version, rwsVersion: dto.rwsVersion },
      dto.analysis,
      dto.jpa,
      dto.title
    );
}

export class AppConfigurationModel implements IAppConfigurationModel {
  constructor(
    public version: {
      bffVersion: string;
      ngxVersion: string;
      rwsVersion: string;
    },
    public analysis: IAnalysisConfig,
    public jpa: IJpaConfig,
    public title: string
  ) {}
}
