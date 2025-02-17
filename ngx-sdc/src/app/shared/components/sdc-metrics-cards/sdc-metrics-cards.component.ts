import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { IMetricAnalysisModel } from 'src/app/core/models/sdc';
import { AnalysisService } from 'src/app/core/services/sdc';
import { SdcMetricInfoComponent } from '../sdc-metric-info';

@Component({
    selector: 'sdc-metrics-cards',
    templateUrl: './sdc-metrics-cards.component.html',
    changeDetection: ChangeDetectionStrategy.OnPush,
    imports: [SdcMetricInfoComponent, CommonModule, TranslateModule]
})
export class SdcMetricsCardsComponent implements OnInit {
  @Input()
  public componentId!: number;

  public analysis: IMetricAnalysisModel[] = [];

  constructor(private analysisService: AnalysisService) {}

  ngOnInit(): void {
    this.analysisService.componentAnalysis(this.componentId).then(data => {
      this.analysis = data.page;
    });
  }
}
