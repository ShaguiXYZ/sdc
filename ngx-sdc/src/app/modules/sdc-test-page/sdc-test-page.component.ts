import { Component, OnInit } from '@angular/core';
import { ValueType } from 'src/app/core/models/sdc';
import { UiDateService } from 'src/app/core/services';
import { AnalysisService } from 'src/app/core/services/sdc';
import { SdcTimeEvolutionMultichartModule } from 'src/app/shared/components';
import { SdcTestRoutingModule } from './sdc-test-page-routing.module';

@Component({
  selector: 'sdc-test-page',
  templateUrl: './sdc-test-page.component.html',
  styleUrls: ['./sdc-test-page.component.scss'],
  standalone: true,
  imports: [SdcTestRoutingModule, SdcTimeEvolutionMultichartModule]
})
export class SdcTestComponent implements OnInit {
  public data: { graph: { axis: string; data: string }[]; type?: ValueType } = { graph: [] };

  constructor(private readonly analysisService: AnalysisService, private readonly dateService: UiDateService) {}

  ngOnInit(): void {
    this.analysisService.metricHistory(2, 31).then(data => {
      this.data = {
        graph: data.page.map(analysis => ({
          axis: this.dateService.dateFormat(analysis.analysisDate),
          data: analysis.analysisValues.metricValue
        })),
        type: this.data.type && ValueType.NUMERIC
      };
    });
  }
}
