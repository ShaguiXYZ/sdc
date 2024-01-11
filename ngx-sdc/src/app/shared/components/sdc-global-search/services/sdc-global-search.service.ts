import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ISummaryViewModel, SummaryViewType } from 'src/app/core/models/sdc';
import { SdcRouteService, SummaryViewService } from 'src/app/core/services/sdc';
import { SdcOverlayService } from '../../sdc-overlay/services';

@Injectable()
export class SdcGlobalSearchService {
  private data$: Subject<ISummaryViewModel[]> = new Subject();

  constructor(
    private readonly summaryViewService: SummaryViewService,
    private readonly overlayService: SdcOverlayService,
    private readonly routeService: SdcRouteService
  ) {}

  public onDataChange(): Subject<ISummaryViewModel[]> {
    return this.data$;
  }

  public getSummaryViews(name: string, types: { [key in SummaryViewType]: boolean }, page?: number, ps?: number): void {
    if (name === '') {
      this.data$.next([]);
      return;
    }

    const selectedTypes = Object.entries(types).reduce((acc, [key, value]) => {
      value && acc.push(key as SummaryViewType);

      return acc;
    }, [] as SummaryViewType[]);

    this.summaryViewService.getSummaryViews(name, selectedTypes, page, ps).then(data => {
      this.data$.next(data.page);
    });
  }

  public goTo(item: ISummaryViewModel): void {
    ({
      [SummaryViewType.COMPONENT]: this.routeService.toComponentById,
      [SummaryViewType.SQUAD]: this.routeService.toSquadById,
      [SummaryViewType.DEPARTMENT]: this.routeService.toDepartmentById
    })[item.type]?.(item.id);

    this.overlayService.defaultOverlayState();
  }
}
