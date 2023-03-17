import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { SdcSummaryModel } from './models';
import { SdcSummaryService } from './services';

@Component({
  selector: 'sdc-home',
  templateUrl: './sdc-summary.component.html',
  styleUrls: ['./sdc-summary.component.scss'],
  providers: [SdcSummaryService]
})
export class SdcSummaryComponent implements OnInit, OnDestroy {
  public summary?: SdcSummaryModel;

  private summaryChange$!: Subscription;

  constructor(private sdcSummaryService: SdcSummaryService) {}

  ngOnInit(): void {
    this.summaryChange$ = this.sdcSummaryService.onDataChange().subscribe(summary => (this.summary = summary));
  }

  ngOnDestroy(): void {
    this.summaryChange$.unsubscribe();
  }
}
