import { TestBed } from '@angular/core/testing';
import { ButtonModel } from '@shagui/ng-shagui/core';
import { AlertModel } from '..';

describe(`AlertModel`, () => {
  let model: any;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: AlertModel, useFactory: (test1: string, test2: string[], test3: ButtonModel[]) => new AlertModel(test1, test2, test3) }
      ]
    });

    model = TestBed.inject(AlertModel);
  });

  it('should exist model when module is compiled', () => {
    expect(model).toBeTruthy();
  });

  it('should set model correctly when getter is called', async () => {
    const dataToSet = ['test'];
    model.desctiptions = dataToSet;

    expect(model.desctiptions).toEqual(dataToSet);
  });
});
