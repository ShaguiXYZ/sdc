import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { ICoverageModel, ISquadModel } from 'src/app/core/models/sdc';
import { ContextDataService } from 'src/app/core/services';
import { SdcComplianceBarCardsComponent, SdcCoveragesComponent } from 'src/app/shared/components';
import { IStateCount } from 'src/app/shared/components/sdc-state-count/model';
import { AppUrls } from 'src/app/shared/config/routing';
import { ContextDataInfo } from 'src/app/shared/constants';
import { ApplicationsContextData, IComplianceModel } from 'src/app/shared/models';
import { SdcSquadSummaryComponent } from './components';
import { SdcSquadsDataModel } from './models';
import { SdcSquadsHomeRoutingModule } from './sdc-squads-home-routing.module';
import { SdcSquadsService } from './services';

@Component({
  selector: 'sdc-squads-home',
  templateUrl: './sdc-squads-home.component.html',
  styleUrls: ['./sdc-squads-home.component.scss'],
  providers: [SdcSquadsService],
  standalone: true,
  imports: [
    SdcComplianceBarCardsComponent,
    SdcCoveragesComponent,
    SdcSquadSummaryComponent,
    CommonModule,
    NxHeadlineModule,
    NxLinkModule,
    SdcSquadsHomeRoutingModule,
    TranslateModule
  ]
})
export class SdcSquadsHomeComponent implements OnInit, OnDestroy {
  public squadsData!: SdcSquadsDataModel;
  public componentsInView: IComplianceModel[] = [];

  private summary$!: Subscription;

  constructor(
    private readonly router: Router,
    private readonly contextDataService: ContextDataService,
    private readonly sdcSummaryService: SdcSquadsService
  ) {}

  ngOnInit(): void {
    this.summary$ = this.sdcSummaryService.onDataChange().subscribe((data: Partial<SdcSquadsDataModel>) => {
      this.squadsData = { ...this.squadsData, ...data };
      this.componentsInView = this.squadsData?.components
        ? this.squadsData.components.slice(0, 3).map(IComplianceModel.fromComponentModel)
        : [];

      this.contextDataService.set(ContextDataInfo.APP_CONFIG, {
        ...this.contextDataService.get(ContextDataInfo.APP_CONFIG),
        title: `Squads | ${this.squadsData?.squad?.name ?? ''}`
      });
    });

    this.sdcSummaryService.loadData();
  }

  ngOnDestroy(): void {
    this.summary$.unsubscribe();
  }

  public complianceClicked(compliance: IComplianceModel): void {
    this.contextDataService.set(ContextDataInfo.METRICS_DATA, { compliance });
    this.router.navigate([AppUrls.metrics]);
  }

  public showAll(): void {
    if (this.squadsData) {
      const applicationsContextData: Partial<ApplicationsContextData> = {
        filter: {
          squad: this.squadsData.squad?.id
        }
      };

      this.contextDataService.set(ContextDataInfo.APPLICATIONS_DATA, applicationsContextData);

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

      this.contextDataService.set(ContextDataInfo.APPLICATIONS_DATA, applicationsContextData);

      this.router.navigate([AppUrls.applications]);
    }
  }
}
