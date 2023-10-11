import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { AvailableMetricStates, emptyFn } from 'src/app/core/lib';
import { IStateCount } from '../../sdc-state-count/model/state-count.model';
import { SdcSquadSummaryComponent } from '../sdc-squad-summary.component';

describe('SdcSquadSummaryComponent', () => {
  let component: SdcSquadSummaryComponent;
  let fixture: ComponentFixture<SdcSquadSummaryComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SdcSquadSummaryComponent],
      imports: [HttpClientModule, RouterTestingModule],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcSquadSummaryComponent);
    component = fixture.componentInstance;
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
