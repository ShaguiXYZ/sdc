import { ButtonModel } from '../../../models/button.model';

export class AlertModel {
  private _descriptions: string[] = [];

  constructor(public title: string, text: string | string[], public buttons: ButtonModel[]) {
    this._descriptions = Array.isArray(text) ? [...text] : [text];
  }

  public get desctiptions(): string[] {
    return this._descriptions;
  }
}
