/* eslint max-classes-per-file: 0 */
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { UiHttpService } from '../http.service';
import { Observable } from 'rxjs/internal/Observable';
import { UiCacheService } from '../../context-data/cache.service';
import { TranslateService } from '@ngx-translate/core';

class MockTranslateService {
  private lang = '';

  setDefaultLang(lang: string): void {
    this.lang = lang;
  }

  stream(key: string | Array<string>, interpolateParams?: object): Observable<string | any> {
    return new Observable<string>();
  }

  instant(key: string): string {
    return key;
  }

  get(key: string) {
    return new Observable<any>();
  }
}

class MockUiCacheService {}

interface DataMock {
  id: number;
  name: string;
}

describe('UiHttpService', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        { provide: TranslateService, useClass: MockTranslateService },
        { provide: UiCacheService, useClass: MockUiCacheService }
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
