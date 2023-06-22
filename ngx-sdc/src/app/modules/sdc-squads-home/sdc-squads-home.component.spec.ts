import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { AvailableMetricStates, emptyFn } from 'src/app/core/lib';
import { SdcSquadsHomeComponent } from './sdc-squads-home.component';
import { SdcSquadsService } from './services';
import { of } from 'rxjs';
import { UiContextDataService } from 'src/app/core/services';
import { Router } from '@angular/router';

describe('SdcSquadsHomeComponent', () => {

  let services: any, spies: any;
  let component: SdcSquadsHomeComponent;
  let fixture: ComponentFixture<SdcSquadsHomeComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SdcSquadsHomeComponent],
      imports: [HttpClientTestingModule, RouterTestingModule, TranslateModule.forRoot()],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [{ provide: SdcSquadsService, useClass: SdcSquadsServiceMock }, {provide: UiContextDataService, useClass: UiContextDataServiceMock}, {provide: Router, useClass: RouterMock}]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcSquadsHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    initServices();
  });

  it('should create the sdc squads home component', () => {
    expect(component).toBeTruthy();
  });

  it('should call availableSquads when onSearchSquadChanged is called', () => {
    component.onSearchSquadChanged('');
    expect(spies.sdcSummaryService.availableSquads).toHaveBeenCalled();
  });

  it('should call set when showAll is called', () => {
    component.showAll();
    expect(spies.contextDataService.set).toHaveBeenCalled();
  });

  it('should call selectedSquad when onClickSquad is called', () => {
    component.onClickSquad({id: 1, name: '', coverage: 1});
    expect(spies.sdcSummaryService.selectedSquad).toHaveBeenCalled();
  });

  it('should call set when onClickStateCount is called', () => {
    component.onClickStateCount({count: 1, state: AvailableMetricStates.ACCEPTABLE});
    expect(spies.contextDataService.set).toHaveBeenCalled();
  });

  it('should call set when complianceClicked is called', () => {
    component.complianceClicked({id:1, name: '', coverage: 2});
    expect(spies.contextDataService.set).toHaveBeenCalled();
  });

  function initServices() {
    services = {
      sdcSummaryService: TestBed.inject(SdcSquadsService),
      contextDataService: TestBed.inject(UiContextDataService)
    };
    initSpies();
  }

  function initSpies() {
    spies = {
      sdcSummaryService: {
        availableSquads: spyOn(services.sdcSummaryService, 'availableSquads'),
        selectedSquad: spyOn(services.sdcSummaryService, 'selectedSquad'),
      },
      contextDataService: {
        set: spyOn(services.contextDataService, 'set')
      }
    };
  }

});

class SdcSquadsServiceMock {
  onDataChange() {
    return of({components: [], squads: []});
  }

  loadData() {}

  availableSquads() {}

  selectedSquad(){}
}

class UiContextDataServiceMock{
  set(){};
  get(){};
}

class RouterMock{
  navigate(){};
}
