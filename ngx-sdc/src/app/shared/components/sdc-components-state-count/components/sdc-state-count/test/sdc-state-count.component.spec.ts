import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from '@shagui/ng-shagui/core';
import { MetricStates } from 'src/app/shared/lib';
import { IStateCount } from '../../../../../models/state-count.model';
import { SdcStateCountComponent } from '../sdc-state-count.component';

describe('SdcStateCountComponent', () => {
  let component: SdcStateCountComponent;
  let fixture: ComponentFixture<SdcStateCountComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [SdcStateCountComponent, RouterTestingModule, TranslateModule.forRoot()],
    providers: [provideHttpClient(withInterceptorsFromDi())]
})
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcStateCountComponent);
    component = fixture.componentInstance;
    const stateCount: IStateCount = {
      count: 1,
      state: MetricStates.PERFECT
    };
    component.stateCount = stateCount;
    fixture.detectChanges();
  });

  it('should create the sdc state count component', () => {
    expect(component).toBeTruthy();
  });

  it('should emit a state count', () => {
    component.onClick();
  });
});
