import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { DepartmentServiceMock } from 'src/app/core/mock/services/sdc/department-service.mock';
import { SquadServiceMock } from 'src/app/core/mock/services/sdc/squad-service.mock';
import { IDepartmentModel, IPageable, ISquadModel } from 'src/app/core/models/sdc';
import { ContextDataService } from 'src/app/core/services';
import { DepartmentService, SquadService } from 'src/app/core/services/sdc';
import { SdcDepartmentsService } from '../sdc-departments-home.service';
import { DepartmentContextDataServiceMock } from './mock/context-data-service.mock';

describe(`SdcDepartmentsService`, () => {
  let service: any;
  let services: any;
  let spies: any;
  const pageDepartment: IPageable<IDepartmentModel> = {
    paging: { pageIndex: 0, pageSize: 1, pages: 1, elements: 1 },
    page: [{ id: 1, name: '', coverage: 1 }]
  };
  const pageSquad: IPageable<ISquadModel> = {
    paging: { pageIndex: 0, pageSize: 1, pages: 1, elements: 1 },
    page: [{ id: 1, name: '', coverage: 1, department: { id: 1, name: '' } }]
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        SdcDepartmentsService,
        { provide: ContextDataService, useClass: DepartmentContextDataServiceMock },
        { provide: DepartmentService, useClass: DepartmentServiceMock },
        { provide: SquadService, useClass: SquadServiceMock }
      ]
    });

    service = TestBed.inject(SdcDepartmentsService);

    initServices();
  });

  it('should exist service when module is compiled', () => {
    expect(service).toBeTruthy();
  });

  it('should call contextDataService set when availableSquads is called case one', async () => {
    spies.departmentService.departments.and.returnValue(Promise.resolve(pageDepartment));
    await service.availableDepartments();
    expect(services.contextDataService.set).toHaveBeenCalled();
  });

  it('should call contextDataService set when availableSquads is called case two', async () => {
    spies.departmentService.departments.and.returnValue(Promise.resolve(pageDepartment));
    await service.availableDepartments('test');
    expect(services.contextDataService.set).toHaveBeenCalled();
  });

  it('should call contextDataService set when availableSquads is called case one', async () => {
    spies.squadService.squads.and.returnValue(Promise.resolve(pageSquad));
    await service.availableSquads({ id: 1 });
    expect(services.contextDataService.set).toHaveBeenCalled();
  });

  it('should call contextDataService set when availableSquads is called case two', async () => {
    spies.squadService.squads.and.returnValue(Promise.resolve(pageSquad));
    await service.availableSquads({ id: 1 }, 'test');
    expect(services.contextDataService.set).toHaveBeenCalled();
  });

  it('should call contextDataService set when loadData is called', async () => {
    spies.squadService.squads.and.returnValue(Promise.resolve(pageSquad));
    spies.departmentService.departments.and.returnValue(Promise.resolve(pageDepartment));
    await service.loadData();
    expect(services.contextDataService.set).toHaveBeenCalled();
  });

  const initServices = () => {
    services = {
      contextDataService: TestBed.inject(ContextDataService),
      departmentService: TestBed.inject(DepartmentService),
      squadService: TestBed.inject(SquadService)
    };
    initSpies();
  };

  const initSpies = () => {
    spies = {
      contextDataService: {
        set: spyOn(services.contextDataService, 'set'),
        get: spyOn(services.contextDataService, 'get')
      },
      departmentService: {
        departments: spyOn(services.departmentService, 'departments')
      },
      squadService: {
        squads: spyOn(services.squadService, 'squads')
      }
    };
  };
});
