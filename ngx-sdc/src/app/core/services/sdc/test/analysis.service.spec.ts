import { TestBed } from '@angular/core/testing';
import { TranslateService } from '@ngx-translate/core';
import { HttpService } from '@shagui/ng-shagui/core';
import { of } from 'rxjs';
import { TranslateServiceMock } from 'src/app/core/mock/services';
import { HttpServiceMock } from 'src/app/core/mock/services/http-service.mock';
import { AnalysisType, IMetricAnalysisDTO, IPageable } from '../../../models/sdc';
import { AnalysisService } from '../analysis.service';

const metricAnalysis: IMetricAnalysisDTO = {
  analysisDate: 1,
  coverage: 1,
  metric: { id: 1, name: '', type: AnalysisType.GIT_XML },
  analysisValues: { metricValue: '' }
};
const pageMetricAnalysis: IPageable<IMetricAnalysisDTO> = {
  paging: { pageIndex: 0, pageSize: 1, pages: 1, elements: 1 },
  page: [metricAnalysis]
};

let service: AnalysisService;
let services: any;
let spies: any;

describe('AnalysisService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpService, useClass: HttpServiceMock },
        { provide: TranslateService, useClass: TranslateServiceMock }
      ]
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

  const initServices = () => {
    services = {
      http: TestBed.inject(HttpService)
    };
    initSpies();
  };

  const initSpies = () => {
    spies = {
      http: {
        get: spyOn(services.http, 'get'),
        post: spyOn(services.http, 'post')
      }
    };
  };
});
