/* eslint max-classes-per-file: 0 */
import { HttpClientModule } from '@angular/common/http';
import { TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { TranslateService } from '@ngx-translate/core';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Observable } from 'rxjs';
import { AppComponent } from './app.component';
import { UiAlertModule } from './core/components/alert/alert.module';
import { UiLoadingModule } from './core/components/loading/loading.module';
import { UiNotificationModule } from './core/components/notification/notification.module';

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

describe('AppComponent', () => {
  beforeEach(
    waitForAsync(() => {
      TestBed.configureTestingModule({
        declarations: [AppComponent],
        imports: [HttpClientModule, RouterTestingModule, UiLoadingModule, UiAlertModule, UiNotificationModule],
        schemas: [NO_ERRORS_SCHEMA],
        providers: [{ provide: TranslateService, useClass: MockTranslateService }]
      }).compileComponents();
    })
  );

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });
});
