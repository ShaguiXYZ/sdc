import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ICoverageModel, ISquadModel } from 'src/app/core/models/sdc';
import { UiContextDataService } from 'src/app/core/services';
import { IComplianceModel, IStateCount } from 'src/app/shared/components';
import { AppUrls } from 'src/app/shared/config/routing';
import { ContextDataInfo } from 'src/app/shared/constants/context-data';
import { ApplicationsContextData } from '../sdc-applications';
import { SdcSquadsDataModel } from './models';
import { SdcSummaryService } from './services';

@Component({
  selector: 'sdc-squads-home',
  templateUrl: './sdc-squads-home.component.html',
  styleUrls: ['./sdc-squads-home.component.scss'],
  providers: [SdcSummaryService]
})
export class SdcSquadsHomeComponent implements OnInit, OnDestroy {
  public squadsData?: SdcSquadsDataModel;
  public componentsInView: IComplianceModel[] = [];

  private summary$!: Subscription;

  constructor(private router: Router, private contextDataService: UiContextDataService, private sdcSummaryService: SdcSummaryService) {}

  ngOnInit(): void {
    this.summary$ = this.sdcSummaryService.onSummaryChange().subscribe(data => {
      this.squadsData = data;
      this.componentsInView = this.squadsData?.components
        ? this.squadsData.components.slice(0, 3).map(IComplianceModel.fromComponentModel)
        : [];
    });

    this.sdcSummaryService.loadData();
  }

  ngOnDestroy(): void {
    this.summary$.unsubscribe();
  }

  public complianceClicked(compliance: IComplianceModel): void {
    this.contextDataService.setContextData(ContextDataInfo.METRICS_DATA, { compliance });
    this.router.navigate([AppUrls.metrics]);
  }

  public showAll(): void {
    if (this.squadsData) {
      const applicationsContextData: Partial<ApplicationsContextData> = {
        filter: {
          squad: this.squadsData.squad?.id
        }
      };

      this.contextDataService.setContextData(ContextDataInfo.APPLICATIONS_DATA, applicationsContextData);

      this.router.navigate([AppUrls.applications]);
    }
  }

  public onSearchSquadChanged(filter: string): void {
    if (this.squadsData) {
      this.sdcSummaryService.availableSquads(filter);
    }
  }

  public onClickSquad(event: ICoverageModel) {
    this.sdcSummaryService.selectedSquad(event as ISquadModel);
  }

  public onClickStateCount(event: IStateCount) {
    if (this.squadsData) {
      const applicationsContextData: Partial<ApplicationsContextData> = {
        filter: {
          squad: this.squadsData.squad?.id,
          coverage: event.state.toString()
        }
      };

      this.contextDataService.setContextData(ContextDataInfo.APPLICATIONS_DATA, applicationsContextData);

      this.router.navigate([AppUrls.applications]);
    }
  }
}
