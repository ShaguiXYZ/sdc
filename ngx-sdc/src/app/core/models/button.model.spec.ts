import { TestBed } from '@angular/core/testing';
import { ButtonModel, TypeButton } from './button.model';

describe(`ButtonModel`, () => {
    let model: any;

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [{provide: ButtonModel, useFactory: (test1: TypeButton, test2: string, test3: any) => {
                return new ButtonModel(test1, test2, test3);
              },}]
        });

        model = TestBed.inject(ButtonModel);
    });

    it('should exist model when module is compiled', () => {
        expect(model).toBeTruthy();
    });
});
