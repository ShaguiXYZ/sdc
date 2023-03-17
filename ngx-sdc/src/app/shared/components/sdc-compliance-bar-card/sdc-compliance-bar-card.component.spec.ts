import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SdcComplianceBarCardComponent } from './sdc-compliance-bar-card.component';

describe('ComplianceBarCardComponent', () => {
  let component: SdcComplianceBarCardComponent;
  let fixture: ComponentFixture<SdcComplianceBarCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SdcComplianceBarCardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SdcComplianceBarCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
