import { Component, EventEmitter, Input, OnInit, Output, ViewChild, ViewContainerRef } from '@angular/core';
import { ICoverageModel } from 'src/app/core/models/sdc';
import { BACKGROUND_GRAPH_COLOR } from '../../constants/colors';

@Component({
  selector: 'sdc-coverage-info',
  templateUrl: './sdc-coverage-info.component.html',
  styleUrls: ['./sdc-coverage-info.component.scss']
})
export class SdcCoverageInfoComponent implements OnInit {
  @ViewChild('coverageRef', { read: ViewContainerRef })
  private coverageRef!: ViewContainerRef;

  @Input()
  public coverage!: ICoverageModel;

  @Input()
  public selectable?: boolean;

  @Input()
  public selected?: boolean;

  @Output()
  public selectCoverage: EventEmitter<ICoverageModel> = new EventEmitter();

  constructor(private readonly vcref: ViewContainerRef) {}

  ngOnInit(): void {
    this.createCoverageComponet();
  }

  public onClick() {
    this.selectCoverage.emit(this.coverage);
  }

  async createCoverageComponet() {
    this.vcref.clear();
    const { SdcCoverageChartComponent } = await import('../sdc-coverage-chart/sdc-coverage-chart.component');
    const coverageChart = this.coverageRef.createComponent(SdcCoverageChartComponent);

    coverageChart.instance.backgroundColor = BACKGROUND_GRAPH_COLOR;
    coverageChart.instance.coverage = { id: this.coverage.id, name: '', coverage: this.coverage.coverage };
    coverageChart.instance.size = 55;
  }
}
