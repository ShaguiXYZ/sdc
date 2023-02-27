import { ButtonModel } from './buttonModel';

export class AlertModel {
  constructor(public title: string, public description: string, public buttons: ButtonModel[]) {}
}
