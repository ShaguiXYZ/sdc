import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { emptyFn } from 'src/app/core/lib';
import { AnalysisType, ValueType } from 'src/app/core/models/sdc';
import { DateService } from 'src/app/core/services';
import { AnalysisService } from 'src/app/core/services/sdc';
import { LANGUAGE_DISTIBUTION_METRIC } from 'src/app/shared/constants';
import { DepartmentSummaryModel } from '../models';

@Injectable()
export class SdcDepartmentSummaryService {
  private tabActions: { fn: (param?: any) => void }[] = [];
  private data$: Subject<Partial<DepartmentSummaryModel>>;

  constructor(private readonly analysisService: AnalysisService, private readonly dateService: DateService) {
    this.data$ = new Subject();
    this.tabActions = [{ fn: emptyFn }, { fn: this.languageDistribution }];
  }

  public tabIndexChange(index: number, param?: any) {
    this.tabActions[index]?.fn(param);
  }

  public onDataChange(): Observable<Partial<DepartmentSummaryModel>> {
    return this.data$.asObservable();
  }

  private languageDistribution = (departmentId: number): void => {
    this.analysisService
      .annualSummary(LANGUAGE_DISTIBUTION_METRIC.name, LANGUAGE_DISTIBUTION_METRIC.type, undefined, undefined, departmentId)
      .then(summary => {
        this.data$.next({
          languageDistribution: {
            graph: summary.page.map(analysis => ({
              axis: this.dateService.dateFormat(analysis.analysisDate),
              data: analysis.analysisValues.metricValue
            })),
            legendPosition: 'right',
            type: ValueType.NUMERIC
          }
        });
      });
  };
}
