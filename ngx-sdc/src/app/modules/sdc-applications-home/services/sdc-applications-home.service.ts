import { Injectable } from '@angular/core';
import { Observable, Subject, firstValueFrom, of } from 'rxjs';
import { _console } from 'src/app/core/lib';
import { IPageable, ISquadModel, ITagModel } from 'src/app/core/models/sdc';
import { ContextDataService } from 'src/app/core/services';
import { ComponentService, SquadService, TagService } from 'src/app/core/services/sdc';
import { ContextDataInfo, ELEMENTS_BY_PAGE } from 'src/app/shared/constants';
import { MetricState, styleByName } from 'src/app/shared/lib';
import { ApplicationsContextData, ApplicationsFilter } from 'src/app/shared/models';
import { SdcApplicationsCoverage, SdcApplicationsDataModel, SdcCoverageRange } from '../models';

@Injectable()
export class SdcApplicationsHomeService {
  public contextData?: ApplicationsContextData;

  private data$: Subject<SdcApplicationsDataModel>;

  constructor(
    private readonly contextDataService: ContextDataService,
    private readonly componetService: ComponentService,
    private readonly squadService: SquadService,
    private readonly tagService: TagService
  ) {
    this.data$ = new Subject();
    this.contextData = this.contextDataService.get(ContextDataInfo.APPLICATIONS_DATA);

    this.filterData(
      this.contextData?.filter?.name,
      this.contextData?.filter?.squad,
      this.contextData?.filter?.tags,
      this.contextData?.filter?.coverage,
      this.contextData?.page ?? 0,
      ELEMENTS_BY_PAGE
    );
  }

  public populateData(filter: ApplicationsFilter, page?: number, showLoading?: boolean): void {
    this.filterData(filter.name, filter.squad, filter.tags, filter.coverage, page ?? 0, ELEMENTS_BY_PAGE, showLoading);
  }

  public onDataChange(): Observable<SdcApplicationsDataModel> {
    return this.data$.asObservable();
  }

  public availableSquads(): Promise<IPageable<ISquadModel>> {
    return this.squadService.squads();
  }

  public availableTags(): Promise<IPageable<ITagModel>> {
    return this.tagService.tags();
  }

  public availableCoverages(): Promise<{ key: string; label: string; style: string }[]> {
    return firstValueFrom(
      of(
        Object.entries(MetricState).map(([key, value]) => ({
          key,
          label: `Component.State.${value.style}`,
          style: styleByName(value.style)
        }))
      )
    );
  }

  private filterData(
    name?: string,
    squadId?: number,
    tags?: string[],
    coverage?: string,
    page?: number,
    ps?: number,
    showLoading?: boolean
  ): void {
    const range: Partial<SdcCoverageRange> = coverage ? { ...{ min: -1 }, ...SdcApplicationsCoverage[coverage] } : { min: -1 };

    this.componetService
      .filter(name, squadId, tags, range.min, range.max, page, ps, showLoading)
      .then(pageable => {
        this.contextDataService.set(
          ContextDataInfo.APPLICATIONS_DATA,
          { ...this.contextData, filter: { coverage, name, squad: squadId, tags }, page },
          { persistent: true }
        );

        this.data$.next({
          squadId,
          name,
          coverage,
          tags,
          components: pageable.page,
          paging: pageable.paging
        });
      })
      .catch(_console.error);
  }
}
