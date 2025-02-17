import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { TranslateModule } from '@ngx-translate/core';
import { ContextDataService } from '@shagui/ng-shagui/core';
import { Subscription } from 'rxjs';
import { AppConfigurationModel, IComponentModel, ICoverageModel, IDepartmentModel, ISquadModel } from 'src/app/core/models/sdc';
import { SdcRouteService } from 'src/app/core/services/sdc';
import { SdcComplianceBarCardsComponent, SdcCoveragesComponent } from 'src/app/shared/components';
import { ContextDataInfo } from 'src/app/shared/constants';
import { ApplicationsContextData, IStateCount } from 'src/app/shared/models';
import { SdcSquadSummaryComponent } from './components';
import { SdcSquadsDataModel } from './models';
import { SdcSquadsService } from './services';

@Component({
    selector: 'sdc-squads-home',
    styleUrls: ['./sdc-squads-home.component.scss'],
    templateUrl: './sdc-squads-home.component.html',
    providers: [SdcSquadsService],
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
  public worstComponents: IComponentModel[] = [];

  private summary$!: Subscription;

  constructor(
    private readonly contextDataService: ContextDataService,
    private readonly routerService: SdcRouteService,
    private readonly summaryService: SdcSquadsService
  ) {}

  ngOnInit(): void {
    this.summary$ = this.summaryService.onDataChange().subscribe((data: Partial<SdcSquadsDataModel>) => {
      this.squadsData = { ...this.squadsData, ...data };
      this.worstComponents = this.squadsData?.components ? this.squadsData.components.slice(0, 3) : [];

      const appConfig = this.contextDataService.get<AppConfigurationModel>(ContextDataInfo.APP_CONFIG);

      this.contextDataService.set(ContextDataInfo.APP_CONFIG, {
        ...appConfig,
        title: `Squads | ${this.squadsData?.squad?.name ?? ''}`
      });
    });

    this.summaryService.loadData();
  }

  ngOnDestroy(): void {
    this.summary$?.unsubscribe();
  }

  public complianceClicked(component: IComponentModel): void {
    this.routerService.toComponent(component);
  }

  public departmentClicked(department: IDepartmentModel): void {
    this.routerService.toDepartment(department);
  }

  public showAll(): void {
    if (this.squadsData) {
      const applicationsContextData: Partial<ApplicationsContextData> = {
        filter: {
          squad: this.squadsData.squad?.id
        }
      };

      this.routerService.toApplications(applicationsContextData);
    }
  }

  public onSearchSquadChanged(filter: string): void {
    if (this.squadsData) {
      this.summaryService.availableSquads(filter);
    }
  }

  public onClickSquad(event: ICoverageModel) {
    this.summaryService.selectedSquad(event as ISquadModel);
  }

  public onClickStateCount(event: IStateCount) {
    if (this.squadsData) {
      const applicationsContextData: Partial<ApplicationsContextData> = {
        filter: {
          squad: this.squadsData.squad?.id,
          metricState: event.state
        }
      };

      this.routerService.toApplications(applicationsContextData);
    }
  }
}
