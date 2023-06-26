import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { SdcTimeEvolutionChartComponent } from '../sdc-time-evolution-chart.component';
import { ValueType } from 'src/app/core/models/sdc';

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
    component.name = 'test';
    component.values = [{ data: '', xAxis: '', color: '', type: ValueType.NUMERIC }];
    component.size = { height: 1, width: 1 };
    fixture.detectChanges();
  });

  it('should create the sdc evolution chart component', () => {
    expect(component).toBeTruthy();
  });
});
