import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DataInfo } from 'src/app/core/interfaces/dataInfo';
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

  constructor(private sdcSummaryService: SdcSummaryService) {}

  ngOnInit(): void {
    this.summary$ = this.sdcSummaryService.onSummaryChange().subscribe(summary => (this.summary = summary));
  }

  ngOnDestroy(): void {
    this.summary$.unsubscribe();
  }
}
