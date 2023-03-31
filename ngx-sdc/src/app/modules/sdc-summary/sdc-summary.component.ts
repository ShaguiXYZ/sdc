import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { DataInfo } from 'src/app/core/interfaces/dataInfo';
import { UiContextDataService } from 'src/app/core/services';
import { IComplianceModel } from 'src/app/shared/components';
import { AppUrls } from 'src/app/shared/config/routing';
import { ContextDataInfo } from 'src/app/shared/constants/context-data';
import { SdcSummaryService } from './services';

@Component({
  selector: 'sdc-home',
  templateUrl: './sdc-summary.component.html',
  styleUrls: ['./sdc-summary.component.scss'],
  providers: [SdcSummaryService]
})
export class SdcSummaryComponent implements OnInit, OnDestroy {
  public summary?: DataInfo;

  private summary$!: Subscription;

  constructor(private router: Router, private contextDataService: UiContextDataService, private sdcSummaryService: SdcSummaryService) {}

  ngOnInit(): void {
    this.summary$ = this.sdcSummaryService.onSummaryChange().subscribe(summary => (this.summary = summary));
  }

  ngOnDestroy(): void {
    this.summary$.unsubscribe();
  }

  public complianceClicked(compliance: IComplianceModel) {
    this.contextDataService.setContextData(ContextDataInfo.METRICS_DATA, { compliance });
    this.router.navigate([AppUrls.metrics]);
  }
}
