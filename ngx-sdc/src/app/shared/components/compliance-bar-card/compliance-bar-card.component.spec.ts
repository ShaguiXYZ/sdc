import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UiComplianceBarCardComponent } from './compliance-bar-card.component';

describe('ComplianceBarCardComponent', () => {
  let component: UiComplianceBarCardComponent;
  let fixture: ComponentFixture<UiComplianceBarCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UiComplianceBarCardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UiComplianceBarCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
