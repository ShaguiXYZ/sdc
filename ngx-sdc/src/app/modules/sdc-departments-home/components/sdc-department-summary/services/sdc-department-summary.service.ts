import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { emptyFn } from 'src/app/core/lib';
import { languageDistributionMock } from 'src/app/core/mock/model/language-distribution.mock';
import { ValueType } from 'src/app/core/models/sdc';
import { UiDateService } from 'src/app/core/services';
import { DepartmentSummaryModel } from '../models';

@Injectable()
export class SdcDepartmentSummaryService {
  private departmentSummaryData: DepartmentSummaryModel;
  private tabActions: { fn: () => void }[] = [];
  private data$: Subject<DepartmentSummaryModel>;

  constructor(private readonly dateService: UiDateService) {
    this.data$ = new Subject();
    this.tabActions = [{ fn: emptyFn }, { fn: this.languageDistribution }];
    this.departmentSummaryData = {};
  }

  public set tabSelected(index: number) {
    this.tabActions[index]?.fn();
    this.departmentSummaryData.selectedTabIndex = index;
  }

  public onDataChange(): Observable<DepartmentSummaryModel> {
    return this.data$.asObservable();
  }

  private languageDistribution = (): void => {
    Promise.resolve(languageDistributionMock).then(history => {
      this.departmentSummaryData.languageDistribution = {
        graph: history.page.map(analysis => ({
          axis: this.dateService.dateFormat(analysis.analysisDate),
          data: analysis.analysisValues.metricValue
        })),
        legendPosition: 'right',
        type: ValueType.NUMERIC
      };

      this.data$.next(this.departmentSummaryData);
    });
  };
}
