/* eslint max-classes-per-file: 0 */
import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { SpyLocation } from '@angular/common/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { AvailableMetricStates, emptyFn } from 'src/app/core/lib';
import { SdcComponentsStateCountComponent } from '../sdc-components-state-count.component';

describe('SdcComponentsStateCountComponent', () => {
  let component: SdcComponentsStateCountComponent;
  let fixture: ComponentFixture<SdcComponentsStateCountComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SdcComponentsStateCountComponent],
      imports: [HttpClientModule, RouterTestingModule, TranslateModule.forRoot()],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [{ provide: Location, useClass: SpyLocation }]
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
    component.onClick({ count: 1, state: AvailableMetricStates.PERFECT });
  });
});
