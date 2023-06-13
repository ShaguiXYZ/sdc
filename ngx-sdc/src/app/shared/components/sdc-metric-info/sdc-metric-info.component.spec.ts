import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from 'src/app/core/lib';
import { AnalysisType } from 'src/app/core/models/sdc/analysis-type.model';
import { IMetricModel } from 'src/app/core/models/sdc/metric.model';
import { SdcMetricInfoComponent } from './sdc-metric-info.component';

describe('SdcMetricInfoComponent', () => {
  let component: SdcMetricInfoComponent;
  let fixture: ComponentFixture<SdcMetricInfoComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SdcMetricInfoComponent],
      imports: [HttpClientModule, RouterTestingModule, TranslateModule.forRoot()],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcMetricInfoComponent);
    component = fixture.componentInstance;
    const metric: IMetricModel = {
      id: 1,
      name: 'Test',
      type: AnalysisType.GIT
    };
    component.metric = metric;
    fixture.detectChanges();
  });

  it('should create the sdc metric info component', () => {
    expect(component).toBeTruthy();
  });
});
