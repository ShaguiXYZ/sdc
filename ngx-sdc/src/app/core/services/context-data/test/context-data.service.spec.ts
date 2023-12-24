import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { TranslateModule } from '@ngx-translate/core';
import { ContextDataService } from '../context-data.service';

describe('ContextDataService', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, TranslateModule.forRoot()]
    })
  );

  it('should create service', () => {
    const service: ContextDataService = TestBed.inject(ContextDataService);
    expect(service).toBeTruthy();
  });

  it('should watch on change', () => {
    const service: ContextDataService = TestBed.inject(ContextDataService);
    service.onDataChange('key');
  });

  it('should set and get the context data', () => {
    const service: ContextDataService = TestBed.inject(ContextDataService);
    service.set('key', { name: 'test' });
    service.get('key');
  });

  it('should delete the context data', () => {
    const service: ContextDataService = TestBed.inject(ContextDataService);
    service.set('key', { name: 'test' });
    service.delete('key');
  });

  it('should delete the context data', () => {
    const service: ContextDataService = TestBed.inject(ContextDataService);
    service.set('key', { name: 'test' });
    service.getConfiguration('key');
  });
});
