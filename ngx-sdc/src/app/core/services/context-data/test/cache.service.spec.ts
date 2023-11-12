import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { TranslateModule } from '@ngx-translate/core';
import { CacheService } from '../cache.service';

describe('CacheService', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, TranslateModule.forRoot()]
    })
  );

  it('should create service', () => {
    const service: CacheService = TestBed.inject(CacheService);
    expect(service).toBeTruthy();
  });

  it('should set and get the cache', () => {
    const service: CacheService = TestBed.inject(CacheService);
    service.set('key', { name: 'test' });
    service.get('key');
  });

  it('should delete the cache', () => {
    const service: CacheService = TestBed.inject(CacheService);
    service.set('key', { name: 'test' });
    service.delete('key');
  });

  it('should convert data to Observable', () => {
    const service: CacheService = TestBed.inject(CacheService);
    service.set('key', { name: 'test' });
    service.asObservable('key');
  });

  it('should convert data to Promise', () => {
    const service: CacheService = TestBed.inject(CacheService);
    service.set('key', { name: 'test' });
    service.asPromise('key');
  });
});
