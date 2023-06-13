/* eslint max-classes-per-file: 0 */
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs/internal/Observable';
import { UiCacheService } from '../context-data/cache.service';
import { UiHttpService } from '../http';
import { ComponentService } from './component.services';

class MockUiHttpService {
  get(key: string) {
    return new Observable<any>();
  }

  post(key: string) {
    return new Observable<any>();
  }
}

class MockUiCacheService {
  delete(id: number) {
    id = 0;
  }
}

interface DataMock {
  id: number;
  name: string;
}

describe('UiHttpService', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        { provide: UiHttpService, useClass: MockUiHttpService },
        { provide: UiCacheService, useClass: MockUiCacheService }
      ]
    })
  );

  it('should create service', () => {
    const service: ComponentService = TestBed.inject(ComponentService);
    expect(service).toBeTruthy();
  });

  it('should get a component from an id', () => {
    const service: ComponentService = TestBed.inject(ComponentService);
    service.component(1);
  });

  it('should get squad components', () => {
    const service: ComponentService = TestBed.inject(ComponentService);
    service.squadComponents(1);
  });

  it('should filter', () => {
    const service: ComponentService = TestBed.inject(ComponentService);
    service.filter('test', 1, 10, 90, 1, 1);
  });

  it('should get component metrics', () => {
    const service: ComponentService = TestBed.inject(ComponentService);
    service.componentMetrics(1);
  });

  it('should get the historical', () => {
    const service: ComponentService = TestBed.inject(ComponentService);
    service.historical(1);
  });

  it('should get delete the squad cache', () => {
    const service: ComponentService = TestBed.inject(ComponentService);
    service.clearSquadCache(1);
  });
});
