import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from '@shagui/ng-shagui/core';
import { NgxEchartsModule } from 'ngx-echarts';
import { DateService } from 'src/app/core/services';
import { MetricStates } from 'src/app/shared/lib';
import { IStateCount } from 'src/app/shared/models';
import { SdcSquadSummaryComponent } from '../sdc-squad-summary.component';
import { SdcSquadSummaryService } from '../services';
import { SdcSquadSummaryServiceMock } from './mock/sdc-squad-summary.service.mock';

describe('SdcSquadSummaryComponent', () => {
  let component: SdcSquadSummaryComponent;
  let fixture: ComponentFixture<SdcSquadSummaryComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [SdcSquadSummaryComponent,
        RouterTestingModule,
        TranslateModule.forRoot(),
        NgxEchartsModule.forRoot({
            echarts: () => import('echarts')
        })],
    providers: [DateService, { provide: SdcSquadSummaryService, useClass: SdcSquadSummaryServiceMock }, provideHttpClient(withInterceptorsFromDi())]
})
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcSquadSummaryComponent);
    component = fixture.componentInstance;
    component.squad = { id: 1, name: 'squad1', department: { id: 1, name: 'department1' } };
    fixture.detectChanges();
  });

  it('should create the squad summary component', () => {
    expect(component).toBeTruthy();
  });

  it('should emit a state count', () => {
    const stateCount: IStateCount = {
      count: 1,
      state: MetricStates.PERFECT
    };

    component.onClickStateCount(stateCount);
  });
});
