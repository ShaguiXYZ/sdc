import { TestBed, waitForAsync } from '@angular/core/testing';
import { ContextValidGuard } from './context-valid.guard';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { UiContextDataService } from '../context-data.service';
import { UiNotificationService } from 'src/app/core/components/notification';
import { TranslateService } from '@ngx-translate/core';
import { RouteConfig } from '../models';
import { NX_CONTEX_CONFIG } from '../constatnts';

describe('ContextValidGuard', () => {
    let contextValidGuard: ContextValidGuard;

    beforeEach(waitForAsync(() => {
        TestBed.configureTestingModule({
            providers: [{ provide: Router, useClass: RouterMock },
            { provide: UiContextDataService, useClass: UiContextDataServiceMock },
            { provide: UiNotificationService, useClass: UiNotificationServiceMock },
            { provide: TranslateService, useClass: TranslateServiceMock },
            { provide: NX_CONTEX_CONFIG, useClass: ContextConfigMock}]

        })
            .compileComponents();
    }));
    beforeEach(() => {
        contextValidGuard = TestBed.inject(ContextValidGuard);
    });

    it('shold get false when call canActivate', () => {
        const mock = <T, P extends keyof T>(obj: Pick<T, P>): T => obj as T;
        const route = mock<ActivatedRouteSnapshot, 'routeConfig'>({
            routeConfig: {
                path: 'test'
            }
        });
        var result = contextValidGuard.canActivate(route, {
            url: '',
            root: new ActivatedRouteSnapshot
        });
        expect(result).toBe(false);
    });

});

class ContextConfigMock {
    home: string =  '';
    urls: RouteConfig = {
        'test': {resetContext: true, requiredData: ['test']}
    };
}

class RouterMock {
    navigate() { };
}

class UiContextDataServiceMock {
    get() { return undefined };
}

class UiNotificationServiceMock {
    error() { };
}

class TranslateServiceMock {
    instant() { };
}