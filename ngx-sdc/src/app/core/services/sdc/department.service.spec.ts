/* eslint max-classes-per-file: 0 */
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { UiHttpService } from '../http';
import { of } from 'rxjs';
import { IDepartmentDTO, IPageable } from '../../models/sdc';
import { DepartmentService } from './department.service';


let pageDepartment: IPageable<IDepartmentDTO> = { paging: { pageIndex: 0, pageSize: 1, pages: 1, elements: 1 }, page: [{id: 1, name: ''}] };

let service: DepartmentService;
let services: any, spies: any;

describe('DepartmentService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{ provide: UiHttpService, useClass: MockUiHttpService }]
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

  function initServices() {
    services = {
      http: TestBed.inject(UiHttpService)
    };
    initSpies();
  }

  function initSpies() {
    spies = {
      http: {
        get: spyOn(services.http, 'get')
      }
    };
  }
});

class MockUiHttpService {
  get() { }
}
