import { TestBed } from '@angular/core/testing';
import { IUserDTO, IUserModel, UserModel } from '../userModel';
import { AppAuthorities, IAuthorityModel } from '../authority.model';

describe('UserModel', () => {
  let model: any;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        {
          provide: UserModel,
          useFactory: (
            userName: string,
            email: string,
            authorities: IAuthorityModel[],
            name: string,
            surname: string,
            secondSurname: string
          ) => new UserModel(userName, email, authorities, name, surname, secondSurname)
        }
      ]
    });

    model = TestBed.inject(UserModel);
  });

  it('should exist model when module is compiled', () => {
    expect(model).toBeTruthy();
  });

  it('should not return a null value when toDto and fromDTO is called case one', () => {
    model.authorities = [{ authority: AppAuthorities.business }, { authority: AppAuthorities.it }];
    const dto: IUserDTO = IUserModel.toDTO(model);
    expect(dto).not.toBeNull();
    const mod: IUserModel = IUserModel.fromDTO(dto);
    expect(mod).not.toBeNull();
  });

  it('should not return a null value when toDto and fromDTO is called case two', () => {
    const dto: IUserDTO = IUserModel.toDTO(model);
    expect(dto).not.toBeNull();
    const mod: IUserModel = IUserModel.fromDTO(dto);
    expect(mod).not.toBeNull();
  });
});
