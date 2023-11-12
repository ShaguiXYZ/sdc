import { TestBed, waitForAsync } from '@angular/core/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/core/components/notification';
import { ContextConfigMock } from 'src/app/core/mock/model/context-config.mock';
import { RouterMock } from 'src/app/core/mock/router.mock';
import { TranslateServiceMock, ContextDataServiceMock } from 'src/app/core/mock/services';
import { NotificationServiceMock } from 'src/app/core/mock/services/notification-service.mock';
import { NX_CONTEX_CONFIG } from '../../constatnts';
import { ContextDataService } from '../../context-data.service';
import { ContextValidGuard } from '../context-valid.guard';

describe('ContextValidGuard', () => {
  let contextValidGuard: ContextValidGuard;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: Router, useClass: RouterMock },
        { provide: ContextDataService, useClass: ContextDataServiceMock },
        { provide: NotificationService, useClass: NotificationServiceMock },
        { provide: TranslateService, useClass: TranslateServiceMock },
        { provide: NX_CONTEX_CONFIG, useClass: ContextConfigMock }
      ]
    }).compileComponents();
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
    const result = contextValidGuard.canActivate(route, {
      url: '',
      root: new ActivatedRouteSnapshot()
    });
    expect(result).toBe(false);
  });
});
