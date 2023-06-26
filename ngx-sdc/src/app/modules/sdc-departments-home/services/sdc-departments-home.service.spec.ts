import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { UiContextDataService } from 'src/app/core/services';
import { DepartmentService, SquadService } from 'src/app/core/services/sdc';
import { SdcDepartmentsService } from './sdc-departments-home.service';
import { ICoverageModel, IDepartmentModel, IPageable, ISquadModel } from 'src/app/core/models/sdc';

describe(`SdcDepartmentsService`, () => {
    let service: any, services: any, spies: any;
    let pageDepartment: IPageable<IDepartmentModel> = {paging: {pageIndex: 0, pageSize: 1, pages: 1, elements: 1}, page: [{id: 1, name: '', coverage: 1}]};
    let pageSquad: IPageable<ISquadModel> = {paging: {pageIndex: 0, pageSize: 1, pages: 1, elements: 1}, page: [{id: 1, name: '', coverage: 1, department: {id: 1, name: ''}}]};
    let department: IDepartmentModel = {id: 1, name: '', coverage: 1};
    let coverage: ICoverageModel = {id: 1, name: '', coverage: 1};

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            providers: [
                SdcDepartmentsService,
                { provide: UiContextDataService, useClass: UiContextDataServiceMock },
                { provide: DepartmentService, useClass: DepartmentServiceMock },
                { provide: SquadService, useClass: SquadServiceMock },
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
        await service.availableSquads({id:1});
        expect(services.contextDataService.set).toHaveBeenCalled();
    });

    it('should call contextDataService set when availableSquads is called case two', async () => {
        spies.squadService.squads.and.returnValue(Promise.resolve(pageSquad));
        await service.availableSquads({id:1}, 'test');
        expect(services.contextDataService.set).toHaveBeenCalled();
    });

    it('should call contextDataService set when loadData is called', async () => {
        spies.squadService.squads.and.returnValue(Promise.resolve(pageSquad));
        spies.departmentService.departments.and.returnValue(Promise.resolve(pageDepartment));
        await service.loadData();
        expect(services.contextDataService.set).toHaveBeenCalled();
    });

    function initServices() {
        services = {
            contextDataService: TestBed.inject(UiContextDataService),
            departmentService: TestBed.inject(DepartmentService),
            squadService: TestBed.inject(SquadService)
        };
        initSpies();
    }

    function initSpies() {
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
            },
        };
    }

    class UiContextDataServiceMock {
        set() { };
        get() { return {squads: coverage, department: department}};
    }

    class DepartmentServiceMock {
        departments() { };
    }

    class SquadServiceMock {
        squads() { };
    }

});