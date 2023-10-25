import { Component, Input, OnInit } from '@angular/core';
import { IMetricAnalysisModel } from 'src/app/core/models/sdc';
import { AnalysisService } from 'src/app/core/services/sdc';

@Component({
  selector: 'sdc-metrics-cards',
  templateUrl: './sdc-metrics-cards.component.html',
  styleUrls: ['./sdc-metrics-cards.component.scss']
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
