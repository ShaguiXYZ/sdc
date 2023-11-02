import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { emptyFn } from 'src/app/core/lib';
import { languageDistributionMock } from 'src/app/core/mock/model/language-distribution.mock';
import { ValueType } from 'src/app/core/models/sdc';
import { UiDateService } from 'src/app/core/services';
import { SquadSummaryModel } from '../models';

@Injectable()
export class SdcSquadSummaryService {
  private squadSummaryData: SquadSummaryModel;
  private tabActions: { fn: () => void }[] = [];
  private data$: Subject<SquadSummaryModel>;

  constructor(private readonly dateService: UiDateService) {
    this.data$ = new Subject();
    this.tabActions = [{ fn: emptyFn }, { fn: this.languageDistribution }];
    this.squadSummaryData = {};
  }

  public set tabSelected(index: number) {
    this.tabActions[index]?.fn();
    this.squadSummaryData.selectedTabIndex = index;
  }

  public onDataChange(): Observable<SquadSummaryModel> {
    return this.data$.asObservable();
  }

  private languageDistribution = (): void => {
    Promise.resolve(languageDistributionMock).then(history => {
      this.squadSummaryData.languageDistribution = {
        graph: history.page.map(analysis => ({
          axis: this.dateService.dateFormat(analysis.analysisDate),
          data: analysis.analysisValues.metricValue
        })),
        legendPosition: 'bottom',
        type: ValueType.NUMERIC
      };

      this.data$.next(this.squadSummaryData);
    });
  };
}
