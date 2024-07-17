import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { emptyFn } from '@shagui/ng-shagui/core';
import { componentModelMock } from 'src/app/core/mock/model/component-model.mock';
import { SdcComplianceBarCardComponent } from '../sdc-compliance-bar-card.component';

describe('SdcComplianceBarCardComponent', () => {
  let component: SdcComplianceBarCardComponent;
  let fixture: ComponentFixture<SdcComplianceBarCardComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [SdcComplianceBarCardComponent, RouterTestingModule, TranslateModule.forRoot()],
    providers: [provideHttpClient(withInterceptorsFromDi())]
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
