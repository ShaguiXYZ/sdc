import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { emptyFn } from 'src/app/core/lib';
import { ValueType } from 'src/app/core/models/sdc';
import { DateService } from 'src/app/core/services';
import { AnalysisService } from 'src/app/core/services/sdc';
import { LANGUAGE_DISTIBUTION_METRIC } from 'src/app/shared/constants';
import { ServiceSummaryModel } from '../models';

@Injectable()
export class SdcSquadSummaryService {
  private tabActions: { fn: (param?: any) => void }[] = [];
  private data$: Subject<Partial<ServiceSummaryModel>>;

  constructor(private readonly analysisService: AnalysisService, private readonly dateService: DateService) {
    this.data$ = new Subject();
    this.tabActions = [{ fn: emptyFn }, { fn: this.languageDistribution }];
  }

  public tabFn(index: number, param?: any) {
    this.tabActions[index]?.fn(param);
  }

  public onDataChange(): Observable<Partial<ServiceSummaryModel>> {
    return this.data$.asObservable();
  }

  private languageDistribution = (squadId: number): void => {
    this.analysisService
      .annualSummary(LANGUAGE_DISTIBUTION_METRIC.name, LANGUAGE_DISTIBUTION_METRIC.type, undefined, squadId)
      .then(history => {
        this.data$.next({
          languageDistribution: {
            graph: history.page.map(analysis => ({
              axis: this.dateService.dateFormat(analysis.analysisDate),
              data: analysis.analysisValues.metricValue
            })),
            legendPosition: 'bottom',
            type: ValueType.NUMERIC
          }
        });
      });
  };
}
