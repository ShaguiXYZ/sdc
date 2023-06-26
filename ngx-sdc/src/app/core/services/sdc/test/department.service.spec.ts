import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpServiceMock } from 'src/app/core/mock/services/http-service.mock';
import { IDepartmentDTO, IPageable } from 'src/app/core/models/sdc';
import { UiHttpService } from '../../http';
import { DepartmentService } from '../department.service';

const pageDepartment: IPageable<IDepartmentDTO> = {
  paging: { pageIndex: 0, pageSize: 1, pages: 1, elements: 1 },
  page: [{ id: 1, name: '' }]
};

let service: DepartmentService;
let services: any;
let spies: any;

describe('DepartmentService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{ provide: UiHttpService, useClass: HttpServiceMock }]
    });
    service = TestBed.inject(DepartmentService);
    initServices();
  });

  it('should create service', () => {
    expect(service).toBeTruthy();
  });

  it('should call http get when analysis is called', async () => {
    spies.http.get.and.returnValue(of(pageDepartment));
    await service.departments();
    expect(spies.http.get).toHaveBeenCalled();
  });

  const initServices = () => {
    services = {
      http: TestBed.inject(UiHttpService)
    };
    initSpies();
  };

  const initSpies = () => {
    spies = {
      http: {
        get: spyOn(services.http, 'get')
      }
    };
  };
});
