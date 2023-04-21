import { ButtonModel } from '../../../models/button.model';

export class AlertModel {
  private _descriptions: string[] = [];

  constructor(public title: string, description: string | string[], public buttons: ButtonModel[]) {
    this._descriptions = Array.isArray(description) ? [...description] : [description];
  }

  public get desctiptions(): string[] {
    return this._descriptions;
  }
}
