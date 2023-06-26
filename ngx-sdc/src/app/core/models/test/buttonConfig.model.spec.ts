import { TestBed } from '@angular/core/testing';
import { ButtonConfig, TypeButton } from '../button.model';

describe(`ButtonConfig`, () => {
  let model: any;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: ButtonConfig, useFactory: (test1: string, test2: string, test3: TypeButton) => new ButtonConfig(test1, test2, test3) }
      ]
    });

    model = TestBed.inject(ButtonConfig);
  });

  it('should exist model when module is compiled', () => {
    expect(model).toBeTruthy();
  });

  it('should set enabled correctly when set enabled is called', () => {
    model.enabled = false;
    expect(model.isEnabled()).toBe(false);
  });

  it('should set visible correctly when set visible is called', () => {
    model.visible = false;
    expect(model.isVisible()).toBe(false);
  });

  it('should throw exception when onClick is called', () => {
    expect(() => {
      model.onClick('');
    }).toThrow(new Error('Method not allowed'));
  });
});
