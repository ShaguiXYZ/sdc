import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { TranslateModule } from '@ngx-translate/core';
import { UiContextDataService } from '../context-data.service';

describe('UiContextDataService', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, TranslateModule.forRoot()]
    })
  );

  it('should create service', () => {
    const service: UiContextDataService = TestBed.inject(UiContextDataService);
    expect(service).toBeTruthy();
  });

  it('should watch on change', () => {
    const service: UiContextDataService = TestBed.inject(UiContextDataService);
    service.onDataChange();
  });

  it('should set and get the context data', () => {
    const service: UiContextDataService = TestBed.inject(UiContextDataService);
    service.set('key', { name: 'test' });
    service.get('key');
  });

  it('should delete the context data', () => {
    const service: UiContextDataService = TestBed.inject(UiContextDataService);
    service.set('key', { name: 'test' });
    service.delete('key');
  });

  it('should delete the context data', () => {
    const service: UiContextDataService = TestBed.inject(UiContextDataService);
    service.set('key', { name: 'test' });
    service.getConfiguration('key');
  });
});
