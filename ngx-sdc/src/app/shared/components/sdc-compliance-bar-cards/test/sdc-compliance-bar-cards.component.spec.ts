import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { SpyLocation } from '@angular/common/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from 'src/app/core/lib';
import { SdcComplianceBarCardsComponent } from '../sdc-compliance-bar-cards.component';

describe('SdcComplianceBarCardsComponent', () => {
  let component: SdcComplianceBarCardsComponent;
  let fixture: ComponentFixture<SdcComplianceBarCardsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SdcComplianceBarCardsComponent],
      imports: [HttpClientModule, RouterTestingModule, TranslateModule.forRoot()],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [{ provide: Location, useClass: SpyLocation }]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcComplianceBarCardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the sdc compliance cards bar component', () => {
    expect(component).toBeTruthy();
  });

  it('should select coverage', () => {
    component.onClick({ id: 1, name: 'test', coverage: 90 });
  });
});
