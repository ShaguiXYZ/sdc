import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from 'src/app/core/lib';
import { SdcHorizontalBarChartComponent } from './sdc-horizontal-bar-chart.component';

describe('SdcHorizontalBarChartComponent', () => {
  let component: SdcHorizontalBarChartComponent;
  let fixture: ComponentFixture<SdcHorizontalBarChartComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SdcHorizontalBarChartComponent],
      imports: [HttpClientModule, RouterTestingModule, TranslateModule.forRoot()],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcHorizontalBarChartComponent);
    component = fixture.componentInstance;
    component.name = 'test';
    component.size = { height: 1, width: 1 };
    component.values = [{ data: '', yAxis: '', color: '' }];
    fixture.detectChanges();
  });

  it('should create the sdc horizontal bar chart component', () => {
    expect(component).toBeTruthy();
  });
});
