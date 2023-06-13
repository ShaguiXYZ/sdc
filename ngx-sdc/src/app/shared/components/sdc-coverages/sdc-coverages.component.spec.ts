/* eslint max-classes-per-file: 0 */
import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { SpyLocation } from '@angular/common/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { SdcCoveragesComponent } from './sdc-coverages.component';
import { emptyFn } from 'src/app/core/lib';

describe('SdcCoveragesComponent', () => {
  let component: SdcCoveragesComponent;
  let fixture: ComponentFixture<SdcCoveragesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SdcCoveragesComponent],
      imports: [HttpClientModule, RouterTestingModule],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [{ provide: Location, useClass: SpyLocation }]
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
    component.onClickCoverage({ id: 1, name: 'test' });
  });
});
