import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CommonModule } from '@angular/common';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { emptyFn } from '@shagui/ng-shagui/core';
import { AnalysisServiceMock } from 'src/app/core/mock/services/sdc/analysis-service.mock';
import { AnalysisType } from 'src/app/core/models/sdc/analysis-type.model';
import { IMetricModel } from 'src/app/core/models/sdc/metric.model';
import { AnalysisService } from 'src/app/core/services/sdc/analysis.service';
import { SdcMetricInfoComponent } from '../sdc-metric-info.component';

describe('SdcMetricInfoComponent', () => {
  let component: SdcMetricInfoComponent;
  let fixture: ComponentFixture<SdcMetricInfoComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [SdcMetricInfoComponent, CommonModule],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [{ provide: AnalysisService, useClass: AnalysisServiceMock }]
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
      type: AnalysisType.GIT_XML
    };
    component.analysis = {
      metric: metric,
      name: metric.name,
      analysisDate: 0,
      analysisValues: { metricValue: '0' },
      coverage: 10,
      blocker: false
    };
    fixture.detectChanges();
  });

  it('should create the sdc metric info component', () => {
    expect(component).toBeTruthy();
  });
});
