import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { TranslateService } from '@ngx-translate/core';
import { TranslateServiceMock } from 'src/app/core/mock/services';
import { CacheServiceMock } from 'src/app/core/mock/services/cache-service.mock';
import { CacheService } from '../../context-data/cache.service';
import { HttpService } from '../http.service';

interface DataMock {
  id: number;
  name: string;
}

describe('HttpService', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        { provide: TranslateService, useClass: TranslateServiceMock },
        { provide: CacheService, useClass: CacheServiceMock }
      ]
    })
  );

  it('should create service', () => {
    const service: HttpService = TestBed.inject(HttpService);
    expect(service).toBeTruthy();
  });

  it('should be get', () => {
    const service: HttpService = TestBed.inject(HttpService);
    service.get('0.0.0.0');
  });

  it('should be post', () => {
    const service: HttpService = TestBed.inject(HttpService);
    service._post('0.0.0.0');
  });

  it('should be put', () => {
    const service: HttpService = TestBed.inject(HttpService);
    service._put('0.0.0.0');
  });

  it('should be delete', () => {
    const service: HttpService = TestBed.inject(HttpService);
    service.delete('0.0.0.0');
  });

  it('should be patch', () => {
    const service: HttpService = TestBed.inject(HttpService);
    service._patch<DataMock>('0.0.0.0', { name: 'data name' });
  });
});
