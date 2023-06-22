import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { SdcSquadsService } from './sdc-squads-home.service';
import { UiContextDataService } from 'src/app/core/services';
import { ComponentService } from 'src/app/core/services/sdc/component.services';
import { SquadService } from 'src/app/core/services/sdc';
import { IComponentModel, ICoverageModel, IPageable, ISquadModel } from 'src/app/core/models/sdc';

describe(`SdcSquadsService`, () => {
    let service: any, services: any, spies: any;
    let coverage: ICoverageModel = {id: 1, name: '', coverage: 1};
    let pageSquad: IPageable<ISquadModel> = {paging: {pageIndex: 0, pageSize: 1, pages: 1, elements: 1}, page: [{id: 1, name: '', coverage: 1, department: {id: 1, name: ''}}]};
    let pageComponent: IPageable<IComponentModel> = {paging: {pageIndex: 0, pageSize: 1, pages: 1, elements: 1}, page: [{id: 1, name: '', coverage: 1, squad: {department: {id: 1, name: ''}, id: 1, name: ''}}]};

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            providers: [
                SdcSquadsService,
                { provide: UiContextDataService, useClass: UiContextDataServiceMock },
                { provide: ComponentService, useClass: ComponentServiceMock },
                { provide: SquadService, useClass: SquadServiceMock },
            ]
        });

        service = TestBed.inject(SdcSquadsService);

        initServices();
    });

    it('should exist service when module is compiled', () => {
        expect(service).toBeTruthy();
    });

    it('should have injected correct services', () => {
        expect(service.http).toEqual(services.http);
        expect(service.componetService).toEqual(services.componetService);
        expect(service.squadService).toEqual(services.squadService);
    });

    it('should call contextDataService set when availableSquads is called case one', async () => {
        service.contextData.squad = coverage;
        spies.squadService.squads.and.returnValue(Promise.resolve(pageSquad));
        await service.availableSquads();
        expect(services.contextDataService.set).toHaveBeenCalled();
    });

    it('should call contextDataService set when availableSquads is called case two', async () => {
        service.contextData.squad = coverage;
        spies.squadService.squads.and.returnValue(Promise.resolve(pageSquad));
        await service.availableSquads('test');
        expect(services.contextDataService.set).toHaveBeenCalled();
    });

    it('should call contextDataService set when selectedSquad is called', async () => {
        service.contextData.squad = coverage;
        spies.componetService.squadComponents.and.returnValue(Promise.resolve(pageComponent));
        await service.selectedSquad({id:1});
        expect(services.contextDataService.set).toHaveBeenCalled();
    });

    it('should call contextDataService set when loadData is called', async () => {
        service.contextData.squad = coverage;
        spies.componetService.squadComponents.and.returnValue(Promise.resolve(pageComponent));
        spies.squadService.squads.and.returnValue(Promise.resolve(pageSquad));
        await service.loadData();
        expect(services.contextDataService.set).toHaveBeenCalled();
    });

    function initServices() {
        services = {
            contextDataService: TestBed.inject(UiContextDataService),
            componetService: TestBed.inject(ComponentService),
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
            componetService: {
                squadComponents: spyOn(services.componetService, 'squadComponents')
            },
            squadService: {
                squads: spyOn(services.squadService, 'squads')
            },
        };
    }

    class UiContextDataServiceMock {
        set() { };
        get() { return {squads: undefined}};
    }

    class ComponentServiceMock {
        squadComponents() { };
    }

    class SquadServiceMock {
        squads() { };
    }

});