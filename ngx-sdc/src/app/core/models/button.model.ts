import { UniqueIds } from '../lib';

/* eslint-disable max-classes-per-file */
export enum TypeButton {
  primary = 'primary',
  secondary = 'secondary',
  tertiary = 'tertiary'
}

export class ButtonModel {
  public id: string;

  constructor(public type: TypeButton, public text: string, public action: () => void = (): void => {}) {
    this.id = UniqueIds._next_();
  }
}

export class ButtonConfig {
  public id: string;

  text?: (event?: any) => string;
  type?: (event?: any) => TypeButton;
  icon?: (event?: any) => string;
  callback?: (event?: any) => any;
  options?: any;
  super?: number | boolean;
  warn?: boolean;
  info?: string;

  constructor(text?: string, icon?: string, type?: TypeButton) {
    this.id = UniqueIds._next_();

    this.text = () => text || '';
    this.type = () => type || TypeButton.tertiary;
    this.icon = () => icon || '';
  }

  isVisible = (element?: any): boolean => true;
  public set visible(value: boolean) {
    this.isVisible = () => value;
  }

  isEnabled = (element?: any): boolean => true;
  public set enabled(value: boolean) {
    this.isEnabled = () => value;
  }

  public onClick(event?: any) {
    if (this.callback && this.isEnabled(event) && this.isVisible(event)) {
      return this.callback(event);
    }

    throw new Error('Method not allowed');
  }
}
