import { Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject, Subscription } from 'rxjs';
import { _console, emptyFn } from 'src/app/core/lib';
import { IMetricAnalysisModel, IPageable, ITagModel, ValueType } from 'src/app/core/models/sdc';
import { ContextDataService, DateService, HttpStatus, SecurityService } from 'src/app/core/services';
import { AnalysisService, ComponentService, DepartmentService, SquadService, TagService } from 'src/app/core/services/sdc';
import { ContextDataInfo, LANGUAGE_DISTIBUTION_METRIC } from 'src/app/shared/constants';
import { SdcMetricsContextData } from 'src/app/shared/models';
import { MetricsDataModel } from '../models';
import { SdcOverlayService } from 'src/app/shared/components/sdc-overlay/services';

@Injectable()
export class SdcMetricsHomeService implements OnDestroy {
  private metricContextData!: SdcMetricsContextData;
  private metricData!: MetricsDataModel;
  private tabActions: { fn: () => void }[] = [];
  private data$: Subject<MetricsDataModel> = new Subject();
  private subscriptions: Subscription[] = [];

  constructor(
    private readonly dateService: DateService,
    private readonly departmentService: DepartmentService,
    private readonly analysisService: AnalysisService,
    private readonly componentService: ComponentService,
    private readonly contextDataService: ContextDataService,
    private readonly overlayService: SdcOverlayService,
    private readonly securityService: SecurityService,
    private readonly squadService: SquadService,
    private readonly tagService: TagService
  ) {
    this.subscriptions.push(this.securityService.onSignInOut().subscribe(() => this.loadInitData()));
    this.tabActions = [{ fn: emptyFn }, { fn: this.languageDistribution }];
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
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

  public async loadInitData(): Promise<MetricsDataModel> {
    this.metricContextData = this.contextDataService.get(ContextDataInfo.METRICS_DATA);
    this.metricData = {
      component: this.metricContextData.component,
      selectedAnalysis: this.metricContextData.selected,
      selectedTabIndex: this.metricContextData.selectedTabIndex
    };

    this.tabSelected = this.metricContextData.selectedTabIndex ?? 0;

    await this.availableTags().catch(_console.error);

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
      .catch(_console.error);
  }

  public analyze = (): void => {
    this.analysisService
      .analize(this.metricData.component.id, {
        [HttpStatus.unauthorized]: this.onUnauthorizedError,
        [HttpStatus.locked]: this.onLockedError
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
            .catch(_console.error);
        }

        this.availableTags();
      })
      .catch(_console.error);
  };

  public async availableTags(): Promise<void> {
    const tags: IPageable<ITagModel> = await this.tagService.componentTags(this.metricData.component.id);

    this.metricData.tags = tags.page;
    this.data$.next(this.metricData);
  }

  public addTag(tag: ITagModel): void {
    this.tagService
      .addTag(this.metricData.component.id, tag.name, {
        [HttpStatus.unauthorized]: this.onUnauthorizedError
      })
      .then(tag => {
        this.metricData.tags = [...(this.metricData.tags ?? []), tag];
        this.data$.next(this.metricData);
      });
  }

  public removeTag(tag: ITagModel): void {
    this.tagService
      .removeTag(this.metricData.component.id, tag.name, {
        [HttpStatus.unauthorized]: this.onUnauthorizedError
      })
      .then(() => {
        this.metricData.tags = this.metricData.tags?.filter(t => t.name !== tag.name);
        this.data$.next(this.metricData);
      });
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
              axis: this.dateService.dateFormat(analysis.analysisDate),
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

  private onUnauthorizedError = (error: any): void => {
    this.overlayService.toggleLogin();
  };

  private onLockedError = (error: any): void => {
    console.log('onLockedError', error);
  };
}
