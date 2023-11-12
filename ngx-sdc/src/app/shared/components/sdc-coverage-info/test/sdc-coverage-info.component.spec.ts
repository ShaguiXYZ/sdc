import { CommonModule } from '@angular/common';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { NgxEchartsModule } from 'ngx-echarts';
import { emptyFn } from 'src/app/core/lib';
import { SdcCoverageInfoComponent } from '../sdc-coverage-info.component';

describe('SdcCoverageInfoComponent', () => {
  let component: SdcCoverageInfoComponent;
  let fixture: ComponentFixture<SdcCoverageInfoComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [SdcCoverageInfoComponent, CommonModule, NgxEchartsModule.forRoot({ echarts: () => import('echarts') })],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents()
      .catch(emptyFn);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdcCoverageInfoComponent);
    component = fixture.componentInstance;
    component.coverage = { id: 1, name: '', coverage: 34 };
    component.selectable = true;
    component.selected = true;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should emit an event', () => {
    spyOn(component.selectCoverage, 'emit');

    const nativeElement = fixture.nativeElement;
    const div = nativeElement.querySelector('.coverage-content');
    div.dispatchEvent(new Event('click'));

    fixture.detectChanges();

    expect(component.selectCoverage.emit).toHaveBeenCalled();
  });
});
