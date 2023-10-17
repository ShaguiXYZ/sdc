import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnDestroy, Output, ViewChild, ViewContainerRef } from '@angular/core';
import { ICoverageModel } from 'src/app/core/models/sdc';
import { BACKGROUND_GRAPH_COLOR } from '../../constants';

@Component({
  selector: 'sdc-coverage-info',
  templateUrl: './sdc-coverage-info.component.html',
  styleUrls: ['./sdc-coverage-info.component.scss']
})
export class SdcCoverageInfoComponent implements OnDestroy, AfterViewInit {
  @ViewChild('coverageViewRef', { read: ViewContainerRef })
  private coverageRef!: ViewContainerRef;

  private observer!: IntersectionObserver;
  private coverageChartVisible = false;

  @Input()
  public coverage!: ICoverageModel;

  @Input()
  public selectable?: boolean;

  @Input()
  public selected?: boolean;

  @Output()
  public selectCoverage: EventEmitter<ICoverageModel> = new EventEmitter();

  constructor(private readonly element: ElementRef, private readonly vcref: ViewContainerRef) {}

  ngOnDestroy(): void {
    this.observer.unobserve(this.element.nativeElement);
  }

  ngAfterViewInit(): void {
    this.observer = new IntersectionObserver(
      entries => {
        entries.forEach(entry => {
          if (!this.coverageChartVisible && entry.isIntersecting) {
            this.createCoverageComponent().then(() => (this.coverageChartVisible = true));
          }
        });
      },
      { rootMargin: '0px', threshold: 0.5 }
    );

    this.observer.observe(this.element.nativeElement);
  }

  public onClick() {
    this.selectCoverage.emit(this.coverage);
  }

  private async createCoverageComponent() {
    this.vcref.clear();
    this.lazyCoverageChart(this.coverageRef, { coverage: this.coverage, size: 55 });
  }

  private lazyCoverageChart = async (
    viewContainerRef: ViewContainerRef,
    options: { backgroundColor?: string; coverage: ICoverageModel; size: number }
  ) => {
    const { SdcCoverageChartComponent } = await import('../sdc-coverage-chart/sdc-coverage-chart.component');
    const coverageChart = viewContainerRef.createComponent(SdcCoverageChartComponent);

    coverageChart.instance.backgroundColor = options.backgroundColor ?? BACKGROUND_GRAPH_COLOR;
    coverageChart.instance.coverage = { id: options.coverage.id, name: '', coverage: options.coverage.coverage };
    coverageChart.instance.size = options.size;
  };
}
