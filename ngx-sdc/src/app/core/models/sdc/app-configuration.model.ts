import packageInfo from 'package.json';

export interface IAnalysisConfig {
  precision: number;
  trendDeep: number;
  trendHeat: number;
}

export interface IViewConfig {
  elementsByPage: number;
}

export interface IAppSecurityConfig {
  enabled: boolean;
}

export interface IAppConfigurationDTO {
  bffVersion: string;
  rwsVersion: string;
  analysis: IAnalysisConfig;
  view: IViewConfig;
  security: IAppSecurityConfig;
  title: string;
  copyright?: string;
}

export interface IAppConfigurationModel {
  version: {
    bffVersion: string;
    ngxVersion: string;
    rwsVersion: string;
  };
  analysis: IAnalysisConfig;
  view: IViewConfig;
  security: IAppSecurityConfig;
  title: string;
  copyright?: string;
}

export namespace IAppConfigurationModel {
  export const fromDTO = (dto: IAppConfigurationDTO): IAppConfigurationModel =>
    new AppConfigurationModel(
      { bffVersion: dto.bffVersion, ngxVersion: packageInfo.version, rwsVersion: dto.rwsVersion },
      dto.analysis,
      dto.view,
      dto.security,
      dto.title,
      dto.copyright
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
    public view: IViewConfig,
    public security: IAppSecurityConfig,
    public title: string,
    public copyright?: string
  ) {}
}
