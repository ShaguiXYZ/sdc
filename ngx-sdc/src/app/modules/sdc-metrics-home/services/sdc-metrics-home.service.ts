import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { _console, emptyFn } from 'src/app/core/lib';
import { IMetricAnalysisModel, ITagModel, ValueType } from 'src/app/core/models/sdc';
import { ContextDataService, DateService } from 'src/app/core/services';
import { AnalysisService, ComponentService, DepartmentService, SquadService, TagService } from 'src/app/core/services/sdc';
import { ContextDataInfo, LANGUAGE_DISTIBUTION_METRIC } from 'src/app/shared/constants';
import { SdcMetricsContextData } from 'src/app/shared/models';
import { MetricsDataModel } from '../models';

@Injectable()
export class SdcMetricsHomeService {
  private metricContextData!: SdcMetricsContextData;
  private metricData!: MetricsDataModel;
  private tabActions: { fn: () => void }[] = [];
  private data$: Subject<MetricsDataModel> = new Subject();

  constructor(
    private readonly dateService: DateService,
    private readonly departmentService: DepartmentService,
    private readonly analysisService: AnalysisService,
    private readonly componentService: ComponentService,
    private readonly contextDataService: ContextDataService,
    private readonly squadService: SquadService,
    private readonly tagService: TagService
  ) {
    this.metricContextData = this.contextDataService.get(ContextDataInfo.METRICS_DATA);
    this.metricData = {
      component: this.metricContextData.component,
      selectedAnalysis: this.metricContextData.selected,
      selectedTabIndex: this.metricContextData.selectedTabIndex
    };
    this.tabActions = [{ fn: emptyFn }, { fn: this.languageDistribution }];
    this.tabSelected = this.metricContextData.selectedTabIndex ?? 0;
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

  public loadInitData(): void {
    this.availableTags();

    this.data$.next(this.metricData);
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
      .analize(this.metricData.component.id)
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

  public availableTags(): void {
    this.tagService.componentTags(this.metricData.component.id).then(tags => {
      this.metricData.tags = tags.page;
      this.data$.next(this.metricData);
    });
  }

  public addTag(tag: ITagModel): void {
    this.tagService.addTag(this.metricData.component.id, tag.name).then(tag => {
      this.metricData.tags = [...(this.metricData.tags ?? []), tag];
      this.data$.next(this.metricData);
    });
  }

  public removeTag(tag: ITagModel): void {
    this.tagService.removeTag(this.metricData.component.id, tag.name).then(() => {
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
}
