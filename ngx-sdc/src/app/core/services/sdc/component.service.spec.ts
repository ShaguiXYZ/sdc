/* eslint max-classes-per-file: 0 */
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { UiCacheService } from '../context-data/cache.service';
import { UiHttpService } from '../http';
import { ComponentService } from './component.services';
import { AnalysisType, IComponentDTO, IMetricDTO, IPageable, IPaging } from '../../models/sdc';
import { IHistoricalCoverage } from '../../models/sdc/historical-coverage.model';
import { of } from 'rxjs';

let paging: IPaging = { pageIndex: 0, pageSize: 1, pages: 1, elements: 1 };
let component: IComponentDTO = { id: 1, name: '', squad: { id: 1, department: { id: 1, name: '' }, name: '' } };
let pageComponent: IPageable<IComponentDTO> = { paging: paging, page: [component] };
let pageMetric: IPageable<IMetricDTO> = { paging: paging, page: [{ id: 1, name: '', type: AnalysisType.GIT }] };
let historicalCoverage: IHistoricalCoverage<IComponentDTO> = { data: component, historical: { paging: paging, page: [{ coverage: 1, analysisDate: 1 }] } };

let service: ComponentService;
let services: any, spies: any;

describe('ComponentService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: UiHttpService, useClass: MockUiHttpService },
        { provide: UiCacheService, useClass: MockUiCacheService }
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

  function initServices() {
    services = {
      http: TestBed.inject(UiHttpService)
    };
    initSpies();
  }

  function initSpies() {
    spies = {
      http: {
        get: spyOn(services.http, 'get')
      }
    };
  }
});

class MockUiHttpService {
  get() { }
}

class MockUiCacheService {
  delete() { }
}
