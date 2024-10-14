import { Injectable } from '@angular/core';
import { emptyFn } from '@shagui/ng-shagui/core';
import { Observable, Subject } from 'rxjs';
import { ValueType } from 'src/app/core/models/sdc';
import { DateService } from 'src/app/core/services';
import { AnalysisService } from 'src/app/core/services/sdc';
import { LANGUAGE_DISTIBUTION_METRIC } from 'src/app/shared/constants';
import { ServiceSummaryModel } from '../models';

@Injectable()
export class SdcDepartmentSummaryService {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  private tabActions: { fn: (param?: any) => void }[] = [];
  private data$: Subject<Partial<ServiceSummaryModel>> = new Subject();

  constructor(
    private readonly analysisService: AnalysisService,
    private readonly dateService: DateService
  ) {
    this.tabActions = [{ fn: emptyFn }, { fn: this.languageDistribution }];
  }

  public tabFn(index: number, param?: unknown) {
    this.tabActions[index]?.fn(param);
  }

  public onDataChange(): Observable<Partial<ServiceSummaryModel>> {
    return this.data$.asObservable();
  }

  private languageDistribution = (departmentId: number): void => {
    this.analysisService
      .annualSummary(LANGUAGE_DISTIBUTION_METRIC.name, LANGUAGE_DISTIBUTION_METRIC.type, undefined, undefined, departmentId)
      .then(summary => {
        this.data$.next({
          languageDistribution: {
            graph: summary.page.map(analysis => ({
              axis: this.dateService.format(analysis.analysisDate),
              data: analysis.analysisValues.metricValue
            })),
            legendPosition: 'bottom',
            type: ValueType.NUMERIC
          }
        });
      });
  };
}
