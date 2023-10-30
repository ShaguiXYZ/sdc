import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnDestroy, Output, ViewChild, ViewContainerRef } from '@angular/core';
import { ICoverageModel } from 'src/app/core/models/sdc';
import { BACKGROUND_CHART_COLOR } from '../../constants';

@Component({
  selector: 'sdc-coverage-info',
  templateUrl: './sdc-coverage-info.component.html',
  styleUrls: ['./sdc-coverage-info.component.scss']
})
export class SdcCoverageInfoComponent implements OnDestroy, AfterViewInit {
  @ViewChild('coverageViewRef', { read: ViewContainerRef })
  private coverageRef!: ViewContainerRef;

  @Input()
  public coverage!: ICoverageModel;

  @Input()
  public selectable?: boolean;

  @Input()
  public selected?: boolean;

  @Output()
  public selectCoverage: EventEmitter<ICoverageModel> = new EventEmitter();

  private observer!: IntersectionObserver;
  private coverageChartVisible = false;

  constructor(private readonly element: ElementRef) {}

  ngOnDestroy(): void {
    this.observer.disconnect();
  }

  ngAfterViewInit(): void {
    this.observer = new IntersectionObserver(
      (entries: IntersectionObserverEntry[]) => {
        entries.forEach(entry => {
          !this.coverageChartVisible &&
            entry.isIntersecting &&
            this.createCoverageComponent(this.coverageRef)
              .then(() => {
                this.coverageChartVisible = true;
              })
              .finally(() => {
                this.observer.unobserve(this.element.nativeElement);
              });
        });
      },
      { rootMargin: '0px', threshold: 0 }
    );

    this.observer.observe(this.element.nativeElement);
  }

  public onClick() {
    this.selectCoverage.emit(this.coverage);
  }

  private async createCoverageComponent(viewContainerRef: ViewContainerRef): Promise<void> {
    viewContainerRef.clear();
    return this.lazyCoverageChart(viewContainerRef, {
      backgroundColor: BACKGROUND_CHART_COLOR,
      coverage: { id: this.coverage.id, name: '', coverage: this.coverage.coverage },
      size: 55
    });
  }

  private lazyCoverageChart = async (viewContainerRef: ViewContainerRef, options: { [key: string]: any }) => {
    const { SdcCoverageChartComponent } = await import('../sdc-coverage-chart/sdc-coverage-chart.component');
    const component = viewContainerRef.createComponent(SdcCoverageChartComponent);

    Object.keys(options).forEach(key => {
      (component.instance as { [key: string]: any })[key] = options[key];
    });
  };
}
