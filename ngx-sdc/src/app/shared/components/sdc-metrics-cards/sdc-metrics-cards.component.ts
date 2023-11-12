import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { IMetricAnalysisModel } from 'src/app/core/models/sdc';
import { AnalysisService } from 'src/app/core/services/sdc';
import { SdcMetricInfoComponent } from '../sdc-metric-info';
import { SdcNoDataComponent } from '../sdc-no-data';

@Component({
  selector: 'sdc-metrics-cards',
  templateUrl: './sdc-metrics-cards.component.html',
  styleUrls: ['./sdc-metrics-cards.component.scss'],
  standalone: true,
  imports: [SdcMetricInfoComponent, SdcNoDataComponent, CommonModule, TranslateModule]
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
