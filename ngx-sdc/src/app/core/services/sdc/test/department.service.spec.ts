import { TestBed } from '@angular/core/testing';
import { TranslateService } from '@ngx-translate/core';
import { HttpService } from '@shagui/ng-shagui/core';
import { of } from 'rxjs';
import { TranslateServiceMock } from 'src/app/core/mock/services';
import { HttpServiceMock } from 'src/app/core/mock/services/http-service.mock';
import { IDepartmentDTO, IPageable } from 'src/app/core/models/sdc';
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
      providers: [
        { provide: HttpService, useClass: HttpServiceMock },
        { provide: TranslateService, useClass: TranslateServiceMock }
      ]
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
      http: TestBed.inject(HttpService)
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
