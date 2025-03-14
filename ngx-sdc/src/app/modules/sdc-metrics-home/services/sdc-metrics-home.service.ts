import { HttpStatusCode } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { ContextDataService, emptyFn } from '@shagui/ng-shagui/core';
import { Observable, Subject, Subscription } from 'rxjs';
import { IMetricAnalysisModel, ITagModel, ValueType } from 'src/app/core/models/sdc';
import { DateService, SecurityService } from 'src/app/core/services';
import { AnalysisService, ComponentService, DepartmentService, SquadService, TagService } from 'src/app/core/services/sdc';
import { SdcOverlayService } from 'src/app/shared/components/sdc-overlay/services';
import { ContextDataInfo, LANGUAGE_DISTIBUTION_METRIC } from 'src/app/shared/constants';
import { SdcMetricsContextData } from 'src/app/shared/models';
import { MetricsDataModel } from '../models';

@Injectable()
export class SdcMetricsHomeService implements OnDestroy {
  private tabActions: { fn: () => void }[] = [];
  private metricContextData!: SdcMetricsContextData;
  private metricData!: MetricsDataModel;
  private data$: Subject<MetricsDataModel> = new Subject();
  private subscriptions: Subscription[] = [];

  constructor(
    private readonly analysisService: AnalysisService,
    private readonly componentService: ComponentService,
    private readonly contextDataService: ContextDataService,
    private readonly dateService: DateService,
    private readonly departmentService: DepartmentService,
    private readonly overlayService: SdcOverlayService,
    private readonly securityService: SecurityService,
    private readonly squadService: SquadService,
    private readonly tagService: TagService
  ) {
    this.subscriptions.push(
      this.securityService.onSignInOut().subscribe(() => {
        this.loadTags();
      })
    );
    this.tabActions = [{ fn: emptyFn }, { fn: this.languageDistribution }];
  }

  public set metricAnalysisSeleted(analysis: IMetricAnalysisModel) {
    this.metricContextData.selected = analysis;
    this.contextDataService.set(ContextDataInfo.METRICS_DATA, this.metricContextData);
  }

  public set tabSelected(index: number) {
    this.tabActions[index]?.fn();

    this.metricContextData.selectedTabIndex = index;
    this.contextDataService.set(ContextDataInfo.METRICS_DATA, this.metricContextData);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  public async loadInitData(): Promise<MetricsDataModel> {
    this.metricContextData = this.contextDataService.get(ContextDataInfo.METRICS_DATA);
    this.metricData = {
      component: this.metricContextData.component,
      selectedAnalysis: this.metricContextData.selected,
      selectedTabIndex: this.metricContextData.selectedTabIndex
    };

    this.loadTags();

    this.tabSelected = this.metricContextData.selectedTabIndex ?? 0;

    return this.metricData;
  }

  public onDataChange(): Observable<MetricsDataModel> {
    return this.data$.asObservable();
  }

  public historicalComponentData(): void {
    this.componentService
      .historical(this.metricData.component.id)
      .then(historical => {
        this.metricData.historical = historical;
        this.data$.next(this.metricData);
      })
      .catch(console.error);
  }

  public analyze = (): void => {
    this.analysisService
      .analize(this.metricData.component.id, {
        [HttpStatusCode.Unauthorized]: this.onUnauthorizedError,
        [HttpStatusCode.Locked]: this.onLockedError
      })
      .then(analysis => {
        if (analysis.page.length) {
          this.componentService
            .component(this.metricData.component.id)
            .then(data => {
              this.metricData.component = data;
              this.analysisService.clearAnalysisCache(this.metricData.component.id);
              this.componentService.clearSquadCache(data.squad.id);
              this.departmentService.clearCache();
              this.squadService.clearCache();
              this.tagService.clearCache();

              this.tabSelected = this.metricContextData.selectedTabIndex ?? 0;

              this.historicalComponentData();
            })
            .catch(console.error);
        }

        this.loadTags().then(() => this.data$.next(this.metricData));
      })
      .catch(console.error);
  };

  public addTag(tag: ITagModel): void {
    this.tagService
      .addTag(this.metricData.component.id, tag.name, {
        [HttpStatusCode.Unauthorized]: this.onUnauthorizedError
      })
      .then(data => {
        this.metricData.tags = [...(this.metricData.tags ?? []), data];
        this.data$.next(this.metricData);
      });
  }

  public removeTag(tag: ITagModel): void {
    this.tagService
      .removeTag(this.metricData.component.id, tag.name, {
        [HttpStatusCode.Unauthorized]: this.onUnauthorizedError
      })
      .then(() => {
        this.metricData.tags = this.metricData.tags?.filter(t => t.name !== tag.name);
        this.data$.next(this.metricData);
      });
  }

  private async loadTags(): Promise<void> {
    this.metricData.tags = (await this.tagService.componentTags(this.metricData.component.id)).page;
  }

  private languageDistribution = (): void => {
    this.analysisService.componentAnalysis(this.metricData.component.id).then(data => {
      const metricAnalysis = data.page.find(
        analysis =>
          analysis.name.toLowerCase() === LANGUAGE_DISTIBUTION_METRIC.name.toLowerCase() &&
          LANGUAGE_DISTIBUTION_METRIC.type === analysis.metric.type
      );

      if (metricAnalysis) {
        this.analysisService.metricHistory(this.metricData.component.id, metricAnalysis.metric.id).then(history => {
          this.metricData.languageDistribution = {
            graph: history.page.map(analysis => ({
              axis: this.dateService.format(analysis.analysisDate),
              data: analysis.analysisValues.metricValue
            })),
            legendPosition: 'bottom',
            type: ValueType.NUMERIC
          };

          this.data$.next(this.metricData);
        });
      }
    });
  };

  private onUnauthorizedError = (): void => {
    this.overlayService.toggleLogin();
  };

  private onLockedError = (error: unknown): void => {
    console.log('onLockedError', error);
  };
}
