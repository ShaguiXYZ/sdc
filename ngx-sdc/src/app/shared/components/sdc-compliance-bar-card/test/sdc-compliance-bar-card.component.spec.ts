import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from 'src/app/core/lib';
import { SdcComplianceBarCardComponent } from '../sdc-compliance-bar-card.component';
import { componentModelMock } from 'src/app/core/mock/model/component-model.mock';

describe('SdcComplianceBarCardComponent', () => {
  let component: SdcComplianceBarCardComponent;
  let fixture: ComponentFixture<SdcComplianceBarCardComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [SdcComplianceBarCardComponent, HttpClientModule, RouterTestingModule, TranslateModule.forRoot()],
      schemas: [NO_ERRORS_SCHEMA],
      providers: []
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcComplianceBarCardComponent);
    component = fixture.componentInstance;
    component.component = componentModelMock;
    fixture.detectChanges();
  });

  it('should create the sdc compliance bar card component', () => {
    expect(component).toBeTruthy();
  });

  it('should emit an event', () => {
    spyOn(component.clickLink, 'emit');

    const nativeElement = fixture.nativeElement;
    const link = nativeElement.querySelector('a');
    link.dispatchEvent(new Event('click'));

    fixture.detectChanges();

    expect(component.clickLink.emit).toHaveBeenCalledWith(componentModelMock);
  });
});
