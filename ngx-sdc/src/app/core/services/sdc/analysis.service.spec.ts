/* eslint max-classes-per-file: 0 */
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs/internal/Observable';
import { UiHttpService } from '../http';
import { AnalysisService } from './analysis.service';

class MockUiHttpService {
  get(key: string) {
    return new Observable<any>();
  }

  post(key: string) {
    return new Observable<any>();
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
      providers: [{ provide: UiHttpService, useClass: MockUiHttpService }]
    })
  );

  it('should create service', () => {
    const service: AnalysisService = TestBed.inject(AnalysisService);
    expect(service).toBeTruthy();
  });

  it('should make an analysis', () => {
    const service: AnalysisService = TestBed.inject(AnalysisService);
    service.analysis(1, 1);
  });

  it('should get a metric history', () => {
    const service: AnalysisService = TestBed.inject(AnalysisService);
    service.metricHistory(1, 1);
  });

  it('should analize the componente', () => {
    const service: AnalysisService = TestBed.inject(AnalysisService);
    service.analize(1);
  });
});
