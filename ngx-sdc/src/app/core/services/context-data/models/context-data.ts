export interface IContextData {
  data: any;
  configuration: IContextDataConfigurtion;
}

export interface IContextDataConfigurtion {
  readonly?: boolean;
  persistent?: boolean;
  referenced?: boolean;
}

export class ContextData implements IContextData {
  private _data: any;
  private _configuration: IContextDataConfigurtion;

  constructor(data: any, configuration: IContextDataConfigurtion = {}) {
    this._data = data;
    this._configuration = configuration;
  }

  get data(): any {
    return this._data;
  }
  set data(value: any) {
    this._data = value;
  }

  get configuration(): IContextDataConfigurtion {
    return this._configuration;
  }

  public protected(): boolean {
    return this.configuration?.persistent || false;
  }
}
