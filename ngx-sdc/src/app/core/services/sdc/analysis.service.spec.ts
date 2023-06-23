/* eslint max-classes-per-file: 0 */
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { UiHttpService } from '../http';
import { AnalysisService } from './analysis.service';
import { of } from 'rxjs';
import { AnalysisType, IMetricAnalysisDTO, IPageable } from '../../models/sdc';

let metricAnalysis: IMetricAnalysisDTO = { analysisDate: 1, coverage: 1, metric: { id: 1, name: '', type: AnalysisType.GIT }, analysisValues: { metricValue: '' } };
let pageMetricAnalysis: IPageable<IMetricAnalysisDTO> = { paging: { pageIndex: 0, pageSize: 1, pages: 1, elements: 1 }, page: [metricAnalysis] };

let service: AnalysisService;
let services: any, spies: any;

describe('AnalysisService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{ provide: UiHttpService, useClass: MockUiHttpService }]
    });
    service = TestBed.inject(AnalysisService);
    initServices();
  });

  it('should create service', () => {
    expect(service).toBeTruthy();
  });

  it('should call http get when analysis is called', async () => {
    spies.http.get.and.returnValue(of(metricAnalysis));
    await service.analysis(1, 1);
    expect(spies.http.get).toHaveBeenCalled();
  });

  it('sshould call http get when metricHistory is called', async () => {
    spies.http.get.and.returnValue(of(pageMetricAnalysis));
    await service.metricHistory(1, 1);
    expect(spies.http.get).toHaveBeenCalled();
  });

  it('should call http get when analize is called', async () => {
    spies.http.post.and.returnValue(of(pageMetricAnalysis));
    await service.analize(1);
    expect(spies.http.post).toHaveBeenCalled();
  });

  function initServices() {
    services = {
      http: TestBed.inject(UiHttpService)
    };
    initSpies();
  }

  function initSpies() {
    spies = {
      http: {
        get: spyOn(services.http, 'get'),
        post: spyOn(services.http, 'post')
      }
    };
  }
});

class MockUiHttpService {
  get() { }
  post() { }
}
