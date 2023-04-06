import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { SquadModel } from 'src/app/core/models/sdc';
import { UiContextDataService } from 'src/app/core/services';
import { IComplianceModel, IStateCount } from 'src/app/shared/components';
import { AppUrls } from 'src/app/shared/config/routing';
import { ContextDataInfo } from 'src/app/shared/constants/context-data';
import { ApplicationsContextData } from '../sdc-applications';
import { SdcSummaryDataModel } from './models';
import { SdcSummaryService } from './services';

@Component({
  selector: 'sdc-home',
  templateUrl: './sdc-summary.component.html',
  styleUrls: ['./sdc-summary.component.scss'],
  providers: [SdcSummaryService]
})
export class SdcSummaryComponent implements OnInit, OnDestroy {
  public summary?: SdcSummaryDataModel;
  public componentsInView: IComplianceModel[] = [];

  private summary$!: Subscription;

  constructor(private router: Router, private contextDataService: UiContextDataService, private sdcSummaryService: SdcSummaryService) {}

  ngOnInit(): void {
    this.summary$ = this.sdcSummaryService.onSummaryChange().subscribe(data => {
      this.summary = data;
      this.componentsInView = this.summary?.components ? this.summary.components.slice(0, 3).map(IComplianceModel.fromComponentModel) : [];
    });

    this.sdcSummaryService.summaryData();
  }

  ngOnDestroy(): void {
    this.summary$.unsubscribe();
  }

  public complianceClicked(compliance: IComplianceModel): void {
    this.contextDataService.setContextData(ContextDataInfo.METRICS_DATA, { compliance });
    this.router.navigate([AppUrls.metrics]);
  }

  public showAll(): void {
    if (this.summary) {
      const applicationsContextData: Partial<ApplicationsContextData> = {
        filter: {
          squad: this.summary.squad?.id
        }
      };

      this.contextDataService.setContextData(ContextDataInfo.APPLICATIONS_DATA, applicationsContextData);

      this.router.navigate([AppUrls.applications]);
    }
  }

  public onSearchSquadChanged(filter: string): void {
    if (this.summary) {
      this.sdcSummaryService.availableSquads(filter);
    }
  }

  public onClickSquad(event: SquadModel) {
    this.sdcSummaryService.selectedSquad(event);
  }

  public onClickStateCount(event: IStateCount) {
    if (this.summary) {
      const applicationsContextData: Partial<ApplicationsContextData> = {
        filter: {
          squad: this.summary.squad?.id,
          coverage: event.state.toString()
        }
      };

      this.contextDataService.setContextData(ContextDataInfo.APPLICATIONS_DATA, applicationsContextData);

      this.router.navigate([AppUrls.applications]);
    }
  }

  private loadData(): void {
    this.sdcSummaryService.availableSquads(this.summary?.filter);
  }
}
