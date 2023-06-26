import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { TranslateModule } from '@ngx-translate/core';
import { UiContextDataService } from '../..';
import { UiStorageService } from '../storage.service';

describe('UiStorageService', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, TranslateModule.forRoot()]
    })
  );

  beforeEach(() => {
    const contextDataService: UiContextDataService = TestBed.inject(UiContextDataService);
    contextDataService.set('key', { data: 'key' });
  });

  it('should create service', () => {
    const service: UiStorageService = TestBed.inject(UiStorageService);
    expect(service).toBeTruthy();
  });

  it('should create a variable in localStorage', () => {
    const service: UiStorageService = TestBed.inject(UiStorageService);
    service.create('key');
  });

  it('should retrieve a variable in localStorage', () => {
    const service: UiStorageService = TestBed.inject(UiStorageService);
    service.create('key');
    service.retrieve('key');
  });

  it('should merge a variable in localStorage', () => {
    const service: UiStorageService = TestBed.inject(UiStorageService);
    service.create('key');
    service.merge('key');
  });
});
