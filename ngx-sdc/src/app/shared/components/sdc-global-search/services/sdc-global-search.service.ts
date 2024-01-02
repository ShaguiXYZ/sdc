import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ISummaryViewModel, SummaryViewType } from 'src/app/core/models/sdc';
import { SdcRouteService, SummaryViewService } from 'src/app/core/services/sdc';

@Injectable()
export class SdcGlobalSearchService {
  private data$: Subject<ISummaryViewModel[]> = new Subject();

  constructor(
    private readonly summaryViewService: SummaryViewService,
    private readonly routeService: SdcRouteService
  ) {}

  public onDataChange(): Subject<ISummaryViewModel[]> {
    return this.data$;
  }

  public getSummaryViews(name: string, types: any = [], page?: number, ps?: number): void {
    if (name === '') {
      this.data$.next([]);
      return;
    }

    this.summaryViewService.getSummaryViews(name, types, page, ps).then(data => {
      this.data$.next(data.page);
    });
  }

  public goTo(item: ISummaryViewModel): void {
    ({
      [SummaryViewType.COMPONENT]: this.routeService.toComponentById,
      [SummaryViewType.SQUAD]: this.routeService.toSquadById,
      [SummaryViewType.DEPARTMENT]: this.routeService.toDepartmentById
    })[item.type]?.(item.id);
  }
}
