import { TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from 'src/app/core/lib';
import { SdcSquadsHomeComponent } from './sdc-squads-home.component';
import { SdcSquadsService } from './services';

class MockSdcSquadsService {}

describe('SdcSquadsHomeComponent', () => {
  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SdcSquadsHomeComponent],
      imports: [HttpClientTestingModule, RouterTestingModule, TranslateModule.forRoot()],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [{ provide: SdcSquadsService, useClass: MockSdcSquadsService }]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  it('should create the sdc squads home component', () => {
    const fixture = TestBed.createComponent(SdcSquadsHomeComponent);
    fixture.detectChanges();
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should show all squads', () => {
    const fixture = TestBed.createComponent(SdcSquadsHomeComponent);
    fixture.detectChanges();
    const app = fixture.debugElement.componentInstance;
    app.showAll();
  });

  it('should show all squads when changed', () => {
    const fixture = TestBed.createComponent(SdcSquadsHomeComponent);
    fixture.detectChanges();
    const app = fixture.debugElement.componentInstance;
    app.onSearchSquadChanged();
  });
});
