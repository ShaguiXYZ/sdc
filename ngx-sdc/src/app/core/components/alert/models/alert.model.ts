import { UniqueIds } from 'src/app/core/lib';
import { ButtonModel } from '../../../models/button.model';

export class AlertModel {
  public id: string;

  private _descriptions: string[] = [];

  constructor(public title: string, text: string | string[], public buttons: ButtonModel[]) {
    this.id = UniqueIds._next_();
    this._descriptions = Array.isArray(text) ? [...text] : [text];
  }

  public get desctiptions(): string[] {
    return this._descriptions;
  }

  public set desctiptions(descriptions) {
    this._descriptions = descriptions;
  }
}
