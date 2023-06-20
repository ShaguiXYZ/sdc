import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { NxDialogService, NxModalModule } from '@aposin/ng-aquila/modal';
import { emptyFn } from 'src/app/core/lib';
import { UiAlertComponent } from './alert.component';
import { TranslateModule } from '@ngx-translate/core';
import { UiAlertService } from './services';
import { of } from 'rxjs';

describe('UiAlertComponent', () => {
  let component: UiAlertComponent;
  let fixture: ComponentFixture<UiAlertComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [UiAlertComponent],
      imports: [NxModalModule.forRoot(), TranslateModule.forRoot()],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        { provide: UiAlertService, useClass: UiAlertServiceMock },
        { provide: NxDialogService, useClass: NxDialogServiceMock }
      ]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UiAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the sdc alert component', () => {
    expect(component).toBeTruthy();
  });
});

class UiAlertServiceMock {
  closeAlert(){};
  onAlert(){
    return of({ descriptions: ['test']});
  };
}

class NxDialogServiceMock {
  open(){};
}
