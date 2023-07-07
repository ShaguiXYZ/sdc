import { TestBed } from '@angular/core/testing';
import { IResourceDTO, IResourceModel, ResourceModel } from '../resource.model';

describe(`ResourceModel`, () => {
  let model: any;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: ResourceModel, useFactory: (name: string, uri: string) => new ResourceModel(name, uri) }
      ]
    });

    model = TestBed.inject(ResourceModel);
  });

  it('should exist model when module is compiled', () => {
    expect(model).toBeTruthy();
  });

  it('should not return a null value when toDto and toModel is called', () => {
    const dto: IResourceDTO = IResourceModel.toDTO(model);
    expect(dto).not.toBeNull();
    const mod: IResourceModel = IResourceModel.toModel(dto);
    expect(mod).not.toBeNull();
  });
});
