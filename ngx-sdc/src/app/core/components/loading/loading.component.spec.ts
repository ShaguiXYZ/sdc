import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { NxModalModule } from '@aposin/ng-aquila/modal';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from 'src/app/core/lib';
import { UiLoadingComponent } from './loading.component';

describe('UiLoadingComponent', () => {
  let component: UiLoadingComponent;
  let fixture: ComponentFixture<UiLoadingComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [UiLoadingComponent],
      imports: [NxModalModule.forRoot(), TranslateModule.forRoot()],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UiLoadingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the sdc loading component', () => {
    expect(component).toBeTruthy();
  });
});
