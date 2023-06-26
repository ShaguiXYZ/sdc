import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { CacheServiceMock } from 'src/app/core/mock/services/cache-service.mock';
import { HttpServiceMock } from 'src/app/core/mock/services/http-service.mock';
import { AnalysisType, IComponentDTO, IMetricDTO, IPageable, IPaging } from 'src/app/core/models/sdc';
import { IHistoricalCoverage } from 'src/app/core/models/sdc/historical-coverage.model';
import { UiCacheService } from '../../context-data';
import { UiHttpService } from '../../http';
import { ComponentService } from '../component.services';

const paging: IPaging = { pageIndex: 0, pageSize: 1, pages: 1, elements: 1 };
const component: IComponentDTO = { id: 1, name: '', squad: { id: 1, department: { id: 1, name: '' }, name: '' } };
const pageComponent: IPageable<IComponentDTO> = { paging, page: [component] };
const pageMetric: IPageable<IMetricDTO> = { paging, page: [{ id: 1, name: '', type: AnalysisType.GIT }] };
const historicalCoverage: IHistoricalCoverage<IComponentDTO> = {
  data: component,
  historical: { paging, page: [{ coverage: 1, analysisDate: 1 }] }
};

let service: ComponentService;
let services: any;
let spies: any;

describe('ComponentService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: UiHttpService, useClass: HttpServiceMock },
        { provide: UiCacheService, useClass: CacheServiceMock }
      ]
    });
    service = TestBed.inject(ComponentService);
    initServices();
  });

  it('should create service', () => {
    expect(service).toBeTruthy();
  });

  it('should call http get when component is called', async () => {
    spies.http.get.and.returnValue(of(component));
    await service.component(1);
    expect(spies.http.get).toHaveBeenCalled();
  });

  it('should call http get when squadComponents is called', async () => {
    spies.http.get.and.returnValue(of(pageComponent));
    await service.squadComponents(1);
    expect(spies.http.get).toHaveBeenCalled();
  });

  it('should call http get when filter is called', async () => {
    spies.http.get.and.returnValue(of(pageComponent));
    await service.filter('test', 1, 1, 1, 1, 1);
    expect(spies.http.get).toHaveBeenCalled();
  });

  it('should call http get when componentMetrics is called', async () => {
    spies.http.get.and.returnValue(of(pageMetric));
    await service.componentMetrics(1);
    expect(spies.http.get).toHaveBeenCalled();
  });

  it('should call http get when historical is called', async () => {
    spies.http.get.and.returnValue(of(historicalCoverage));
    await service.historical(1);
    expect(spies.http.get).toHaveBeenCalled();
  });

  const initServices = () => {
    services = {
      http: TestBed.inject(UiHttpService)
    };
    initSpies();
  };

  const initSpies = () => {
    spies = {
      http: {
        get: spyOn(services.http, 'get')
      }
    };
  };
});
