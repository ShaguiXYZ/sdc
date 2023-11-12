import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from 'src/app/core/lib';
import { UiDateService } from 'src/app/core/services';
import { IStateCount } from 'src/app/shared/components/sdc-state-count/model';
import { AvailableMetricStates } from 'src/app/shared/lib';
import { SdcSquadSummaryComponent } from '../sdc-squad-summary.component';
import { SdcSquadSummaryService } from '../services';
import { SdcSquadSummaryServiceMock } from './mock/sdc-squad-summary.service.mock';
import { NgxEchartsModule } from 'ngx-echarts';

describe('SdcSquadSummaryComponent', () => {
  let component: SdcSquadSummaryComponent;
  let fixture: ComponentFixture<SdcSquadSummaryComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [
        SdcSquadSummaryComponent,
        HttpClientModule,
        RouterTestingModule,
        TranslateModule.forRoot(),
        NgxEchartsModule.forRoot({
          echarts: () => import('echarts')
        })
      ],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [UiDateService, { provide: SdcSquadSummaryService, useClass: SdcSquadSummaryServiceMock }]
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
      state: AvailableMetricStates.PERFECT
    };

    component.onClickStateCount(stateCount);
  });
});
