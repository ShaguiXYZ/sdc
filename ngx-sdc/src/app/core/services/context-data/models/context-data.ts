export interface IContextData {
  data: any;
  configuration: IContextDataConfigurtion;
}

export interface IContextDataConfigurtion {
  readonly?: boolean;
  persistent?: boolean;
}

export class ContextData implements IContextData {
  private _data: any;
  private _configuration: IContextDataConfigurtion;

  constructor(data: any, configuration: IContextDataConfigurtion = {}, public isCore: boolean = false) {
    this._data = data;
    this._configuration = configuration;
  }

  get data(): any {
    return this._data;
  }
  set data(value: any) {
    this._data = value;
  }

  get configuration() {
    return this._configuration;
  }

  public protected(): boolean {
    return this.isCore || this.configuration?.persistent || false;
  }
}
