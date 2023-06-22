import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { UiAlertService } from './alert.service';
import { TranslateService } from '@ngx-translate/core';

describe(`UiAlertService`, () => {
    let service: any, services: any, spies: any;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            providers: [
                UiAlertService,
                { provide: TranslateService, useClass: TranslateServiceMock }
            ]
        });

        service = TestBed.inject(UiAlertService);

        initServices();
    });

    it('should exist service when module is compiled', () => {
        expect(service).toBeTruthy();
    });

    it('should call translateService instant when confirm is called case one', () => {
        service.confirm({text: ''}, () => {}, { okText: '', cancelText: '' }, true);
        expect(services.translateService.instant).toHaveBeenCalled();
    });

    it('should call translateService instant when confirm is called case two', () => {
        service.confirm({text: ['', '']}, () => {}, { okText: '', cancelText: '' }, true);
        expect(services.translateService.instant).toHaveBeenCalled();
    });

    it('should return not null when onAlert is called', () => {
        var result = service.onAlert();
        expect(result).not.toBeNull();
    });

    function initServices() {
        services = {
            translateService: TestBed.inject(TranslateService)
        };
        initSpies();
    }

    function initSpies() {
        spies = {
            translateService: {
                instant: spyOn(services.translateService, 'instant')
            }
        };
    }

    class TranslateServiceMock {
        instant() { };
    }

});