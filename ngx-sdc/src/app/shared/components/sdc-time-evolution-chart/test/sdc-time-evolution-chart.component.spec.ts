import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { SdcTimeEvolutionChartComponent } from '../sdc-time-evolution-chart.component';

describe('SdcBreadcrumbComponent', () => {
  let component: SdcTimeEvolutionChartComponent;
  let fixture: ComponentFixture<SdcTimeEvolutionChartComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SdcTimeEvolutionChartComponent],
      imports: [HttpClientModule, RouterTestingModule],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcTimeEvolutionChartComponent);
    component = fixture.componentInstance;
    component.size = { height: 1, width: 1 };
    component.config = { axis: {}, data: [{ values: { value: '0' } }, { values: [] }, { values: [] }] };
    fixture.detectChanges();
  });

  it('should create the sdc evolution chart component', () => {
    expect(component).toBeTruthy();
  });
});
