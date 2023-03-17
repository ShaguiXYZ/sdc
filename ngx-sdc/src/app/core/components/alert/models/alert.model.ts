import { ButtonModel } from '../../../models/button.model';

export class AlertModel {
  constructor(public title: string, public description: string, public buttons: ButtonModel[]) {}
}
