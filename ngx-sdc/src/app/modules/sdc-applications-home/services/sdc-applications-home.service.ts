import { Injectable } from '@angular/core';
import { Observable, Subject, firstValueFrom, of } from 'rxjs';
import { _console } from 'src/app/core/lib';
import { IAppConfiguration, IPageable, ISquadModel, ITagModel } from 'src/app/core/models/sdc';
import { ContextDataService } from 'src/app/core/services';
import { ComponentService, SquadService, TagService } from 'src/app/core/services/sdc';
import { ContextDataInfo, ELEMENTS_BY_PAGE } from 'src/app/shared/constants';
import { MetricState, MetricStates, rangeByState, styleByName } from 'src/app/shared/lib';
import { ApplicationsContextData, ApplicationsFilter, SdcRange } from 'src/app/shared/models';
import { SdcApplicationsDataModel } from '../models';

@Injectable()
export class SdcApplicationsHomeService {
  public contextData?: ApplicationsContextData;
  public emementsByPage!: number;

  private data$: Subject<SdcApplicationsDataModel> = new Subject();

  constructor(
    private readonly contextDataService: ContextDataService,
    private readonly componetService: ComponentService,
    private readonly squadService: SquadService,
    private readonly tagService: TagService
  ) {
    const appConfig = this.contextDataService.get<IAppConfiguration>(ContextDataInfo.APP_CONFIG);
    this.emementsByPage = appConfig.jpa.elementsByPage;
    this.contextData = this.contextDataService.get(ContextDataInfo.APPLICATIONS_DATA);

    this.filterData(
      this.contextData?.filter?.name,
      this.contextData?.filter?.squad,
      this.contextData?.filter?.tags,
      this.contextData?.filter?.metricState,
      this.contextData?.page ?? 0,
      this.emementsByPage
    );
  }

  public populateData(filter: ApplicationsFilter, page?: number, showLoading?: boolean): void {
    const appConfig = this.contextDataService.get<IAppConfiguration>(ContextDataInfo.APP_CONFIG);

    this.filterData(filter.name, filter.squad, filter.tags, filter.metricState, page ?? 0, appConfig.jpa.elementsByPage, showLoading);
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
    metricState?: MetricStates,
    page?: number,
    ps: number = ELEMENTS_BY_PAGE,
    showLoading?: boolean
  ): void {
    const range: Partial<SdcRange> | undefined = metricState ? rangeByState(MetricStates[metricState]) : undefined;

    this.componetService
      .filter(name, squadId, tags, range?.min, range?.max, page, ps, showLoading)
      .then(pageable => {
        this.contextDataService.set(
          ContextDataInfo.APPLICATIONS_DATA,
          { ...this.contextData, filter: { metricState, name, squad: squadId, tags }, page },
          { persistent: true }
        );

        this.data$.next({
          squadId,
          name,
          metricState,
          tags,
          components: pageable.page,
          paging: pageable.paging
        });
      })
      .catch(_console.error);
  }
}
