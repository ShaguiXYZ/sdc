import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { TranslateModule } from '@ngx-translate/core';
import { ContextDataService } from '../..';
import { StorageService } from '../storage.service';

describe('StorageService', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, TranslateModule.forRoot()]
    })
  );

  beforeEach(() => {
    const contextDataService: ContextDataService = TestBed.inject(ContextDataService);
    contextDataService.set('key', { data: 'key' });
  });

  it('should create service', () => {
    const service: StorageService = TestBed.inject(StorageService);
    expect(service).toBeTruthy();
  });

  it('should create a variable in localStorage', () => {
    const service: StorageService = TestBed.inject(StorageService);
    service.create('key');
  });

  it('should retrieve a variable in localStorage', () => {
    const service: StorageService = TestBed.inject(StorageService);
    service.create('key');
    service.retrieve('key');
  });

  it('should merge a variable in localStorage', () => {
    const service: StorageService = TestBed.inject(StorageService);
    service.create('key');
    service.merge('key');
  });
});
