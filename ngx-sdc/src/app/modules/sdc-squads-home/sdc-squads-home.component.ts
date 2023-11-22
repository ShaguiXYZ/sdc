import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { IComponentModel, ICoverageModel, IDepartmentModel, ISquadModel } from 'src/app/core/models/sdc';
import { ContextDataService } from 'src/app/core/services';
import { SdcComplianceBarCardsComponent, SdcCoveragesComponent } from 'src/app/shared/components';
import { AppUrls } from 'src/app/shared/config/routing';
import { ContextDataInfo } from 'src/app/shared/constants';
import { ApplicationsContextData, IStateCount } from 'src/app/shared/models';
import { SdcSquadSummaryComponent } from './components';
import { SdcSquadsDataModel } from './models';
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
    TranslateModule
  ]
})
export class SdcSquadsHomeComponent implements OnInit, OnDestroy {
  public squadsData!: SdcSquadsDataModel;
  public componentsInView: IComponentModel[] = [];

  private summary$!: Subscription;

  constructor(
    private readonly router: Router,
    private readonly contextDataService: ContextDataService,
    private readonly sdcSummaryService: SdcSquadsService
  ) {}

  ngOnInit(): void {
    this.summary$ = this.sdcSummaryService.onDataChange().subscribe((data: Partial<SdcSquadsDataModel>) => {
      this.squadsData = { ...this.squadsData, ...data };
      this.componentsInView = this.squadsData?.components ? this.squadsData.components.slice(0, 3) : [];

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

  public complianceClicked(component: IComponentModel): void {
    this.contextDataService.set(ContextDataInfo.METRICS_DATA, { component });
    this.router.navigate([AppUrls.metrics]);
  }

  public departmentClicked(department: IDepartmentModel): void {
    this.contextDataService.set(ContextDataInfo.DEPARTMENTS_DATA, { department });
    this.router.navigate([AppUrls.departments]);
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
