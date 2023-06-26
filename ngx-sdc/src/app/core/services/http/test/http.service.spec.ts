import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { TranslateService } from '@ngx-translate/core';
import { TranslateServiceMock } from 'src/app/core/mock/services';
import { CacheServiceMock } from 'src/app/core/mock/services/cache-service.mock';
import { UiCacheService } from '../../context-data/cache.service';
import { UiHttpService } from '../http.service';

interface DataMock {
  id: number;
  name: string;
}

describe('UiHttpService', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        { provide: TranslateService, useClass: TranslateServiceMock },
        { provide: UiCacheService, useClass: CacheServiceMock }
      ]
    })
  );

  it('should create service', () => {
    const service: UiHttpService = TestBed.inject(UiHttpService);
    expect(service).toBeTruthy();
  });

  it('should be get', () => {
    const service: UiHttpService = TestBed.inject(UiHttpService);
    service.get('0.0.0.0');
  });

  it('should be post', () => {
    const service: UiHttpService = TestBed.inject(UiHttpService);
    service._post('0.0.0.0');
  });

  it('should be put', () => {
    const service: UiHttpService = TestBed.inject(UiHttpService);
    service._put('0.0.0.0');
  });

  it('should be delete', () => {
    const service: UiHttpService = TestBed.inject(UiHttpService);
    service.delete('0.0.0.0');
  });

  it('should be patch', () => {
    const service: UiHttpService = TestBed.inject(UiHttpService);
    service._patch<DataMock>('0.0.0.0', { name: 'data name' });
  });
});
