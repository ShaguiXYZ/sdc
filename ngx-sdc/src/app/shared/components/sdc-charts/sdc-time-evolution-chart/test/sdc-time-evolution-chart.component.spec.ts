import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/compiler';
import { SdcTimeEvolutionChartComponent } from '../sdc-time-evolution-chart.component';
import { NgxEchartsModule } from 'ngx-echarts';

describe('SdcBreadcrumbComponent', () => {
  let component: SdcTimeEvolutionChartComponent;
  let fixture: ComponentFixture<SdcTimeEvolutionChartComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [
        SdcTimeEvolutionChartComponent,
        HttpClientModule,
        RouterTestingModule,
        NgxEchartsModule.forRoot({
          echarts: () => import('echarts')
        })
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
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
