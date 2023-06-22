import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { emptyFn } from 'src/app/core/lib';
import { AnalysisType } from 'src/app/core/models/sdc/analysis-type.model';
import { IMetricModel } from 'src/app/core/models/sdc/metric.model';
import { SdcMetricInfoComponent } from './sdc-metric-info.component';
import { CommonModule } from '@angular/common';
import { AnalysisService } from 'src/app/core/services/sdc/analysis.service';

describe('SdcMetricInfoComponent', () => {
  let component: SdcMetricInfoComponent;
  let fixture: ComponentFixture<SdcMetricInfoComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SdcMetricInfoComponent],
      imports: [CommonModule],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [{provide: AnalysisService, useClass: AnalysisServiceMock}]
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
    component.componentId = 1;
    fixture.detectChanges();
  });

  it('should create the sdc metric info component', () => {
    expect(component).toBeTruthy();
  });
});

class AnalysisServiceMock {
  analysis() {
    return Promise.resolve({analysisDate: 1, coverage: 1, metric: {}, analysisValues: {}});
  }
}
