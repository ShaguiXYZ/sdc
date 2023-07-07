import { TestBed } from '@angular/core/testing';
import { AppAuthorities, AuthorityModel, IAuthorityDTO, IAuthorityModel } from '../authority.model';

describe(`AuthorityModel`, () => {
  let model: any;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: AuthorityModel, useFactory: (authority: AppAuthorities) => new AuthorityModel(authority) }
      ]
    });

    model = TestBed.inject(AuthorityModel);
  });

  it('should exist model when module is compiled', () => {
    expect(model).toBeTruthy();
  });

  it('should not return a null value when toDto and toModel is called', () => {
    const dto: IAuthorityDTO = IAuthorityModel.toDTO(model);
    expect(dto).not.toBeNull();
    const mod: AuthorityModel = IAuthorityModel.toModel(dto);
    expect(mod).not.toBeNull();
  });
});
