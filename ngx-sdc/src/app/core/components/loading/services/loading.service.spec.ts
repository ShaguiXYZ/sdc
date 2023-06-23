import { NO_ERRORS_SCHEMA } from "@angular/core";
import { TestBed } from "@angular/core/testing";
import { UiLoadingService } from "./loading.service";

describe(`UiLoadingService`, () => {
    let service: any, spies: any;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            providers: [
                UiLoadingService
            ]
        });

        service = TestBed.inject(UiLoadingService);
        initSpies();
    });

    it('should exist service when module is compiled', () => {
        expect(service).toBeTruthy();
    });

    it('should call emit with boolean true param when showloading is called', () => {
        service.showLoading = true;
        expect(spies.emit).toHaveBeenCalledWith(true);
    });

    it('should call emit with boolean false param when showloading is called', () => {
        service.showLoading = true;
        service.showLoading = false;
        expect(spies.emit).toHaveBeenCalledWith(false);
    });

    it('should return false when showloading is called', () => {
        expect(service.showLoading).toBeFalse();
    });

    function initSpies() {
        spies = {
            emit: spyOn(service.uiShowLoading, 'emit')
        };
    }

});
