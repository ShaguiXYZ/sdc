import { TestBed } from '@angular/core/testing';
import { ISessionDTO, ISessionModel, SessionModel } from '../session.model';

describe(`SessionModel`, () => {
  let model: any;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: SessionModel, useFactory: (sid: string, token: string) => new SessionModel(sid, token) }
      ]
    });

    model = TestBed.inject(SessionModel);
  });

  it('should exist model when module is compiled', () => {
    expect(model).toBeTruthy();
  });

  it('should not return a null value when toDto and toModel is called', () => {
    const dto: ISessionDTO = ISessionModel.toDTO(model);
    expect(dto).not.toBeNull();
    const mod: ISessionModel = ISessionModel.toModel(dto);
    expect(mod).not.toBeNull();
  });
});
