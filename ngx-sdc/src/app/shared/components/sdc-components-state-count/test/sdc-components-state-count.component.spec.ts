/* eslint max-classes-per-file: 0 */
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { SpyLocation } from '@angular/common/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from '@shagui/ng-shagui/core';
import { MetricStates } from 'src/app/shared/lib';
import { SdcComponentsStateCountComponent } from '../sdc-components-state-count.component';

describe('SdcComponentsStateCountComponent', () => {
  let component: SdcComponentsStateCountComponent;
  let fixture: ComponentFixture<SdcComponentsStateCountComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [SdcComponentsStateCountComponent, RouterTestingModule, TranslateModule.forRoot()],
    providers: [{ provide: Location, useClass: SpyLocation }, provideHttpClient(withInterceptorsFromDi())]
})
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcComponentsStateCountComponent);
    component = fixture.componentInstance;
    component.components = [{ id: 1, name: '', coverage: 1 }];
    fixture.detectChanges();
  });

  it('should create the sdc components state count component', () => {
    expect(component).toBeTruthy();
  });

  it('should select coverage', () => {
    component.onClick({ count: 1, state: MetricStates.PERFECT });
  });
});
