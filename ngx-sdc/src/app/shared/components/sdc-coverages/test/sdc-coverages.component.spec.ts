/* eslint max-classes-per-file: 0 */
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { SpyLocation } from '@angular/common/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from '@shagui/ng-shagui/core';
import { SdcCoveragesComponent } from '../sdc-coverages.component';

describe('SdcCoveragesComponent', () => {
  let component: SdcCoveragesComponent;
  let fixture: ComponentFixture<SdcCoveragesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      imports: [SdcCoveragesComponent, RouterTestingModule, TranslateModule.forRoot()],
      providers: [{ provide: Location, useClass: SpyLocation }, provideHttpClient(withInterceptorsFromDi())]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcCoveragesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the sdc coverages component', () => {
    expect(component).toBeTruthy();
  });

  it('should select coverage', () => {
    const coverage = { id: 1, name: 'test' } as any;
    spyOn(component.selectCoverage, 'emit');

    component.onClickCoverage(coverage);

    expect(component.selected).toBe(coverage.id);
    expect(component.selectCoverage.emit).toHaveBeenCalledWith(coverage);
  });
});
