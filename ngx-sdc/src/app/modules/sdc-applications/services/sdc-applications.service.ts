import { Injectable } from '@angular/core';
import { Observable, Subject, firstValueFrom, of } from 'rxjs';
import { MetricState, _console, styleByName } from 'src/app/core/lib';
import { IPageable, ISquadModel } from 'src/app/core/models/sdc';
import { UiContextDataService } from 'src/app/core/services';
import { ELEMENTS_BY_PAGE } from 'src/app/core/services/http';
import { ComponentService, SquadService } from 'src/app/core/services/sdc';
import { IComplianceModel } from 'src/app/shared/components';
import { ContextDataInfo } from 'src/app/shared/constants/context-data';
import { ApplicationsContextData, ApplicationsFilter, SdcApplicationsCoverage, SdcApplicationsDataModel } from '../models';

@Injectable()
export class SdcApplicationsService {
  public contextData?: ApplicationsContextData;

  private subject$: Subject<SdcApplicationsDataModel>;

  constructor(
    private contextDataService: UiContextDataService,
    private componetService: ComponentService,
    private squadService: SquadService
  ) {
    this.subject$ = new Subject();
    this.contextData = this.contextDataService.get(ContextDataInfo.APPLICATIONS_DATA);

    this.squadData(
      this.contextData?.filter?.name,
      this.contextData?.filter?.squad,
      this.contextData?.filter?.coverage,
      this.contextData?.page || 0,
      ELEMENTS_BY_PAGE
    );
  }

  public populateData(filter: ApplicationsFilter, page?: number): void {
    this.squadData(filter.name, filter.squad, filter.coverage, page || 0, ELEMENTS_BY_PAGE);
  }

  public onDataChange(): Observable<SdcApplicationsDataModel> {
    return this.subject$.asObservable();
  }

  public availableSquads(): Promise<IPageable<ISquadModel>> {
    return this.squadService.squads();
  }

  public availableCoverages(): Promise<{ key: string; label: string; style: string }[]> {
    return firstValueFrom(
      of(
        Object.keys(MetricState).map(key => ({
          key,
          label: `Component.State.${MetricState[key].style}`,
          style: styleByName(MetricState[key].style)
        }))
      )
    );
  }

  private squadData(name?: string, squadId?: number, coverage?: string, page?: number, ps?: number): void {
    const range = coverage ? SdcApplicationsCoverage[coverage] : undefined;

    this.componetService
      .filter(name, squadId, range?.min, range?.max, page, ps)
      .then(pageable => {
        this.contextDataService.set(
          ContextDataInfo.APPLICATIONS_DATA,
          { ...this.contextData, filter: { coverage, name, squad: squadId }, page },
          { persistent: true }
        );

        this.subject$.next({
          squadId,
          name,
          coverage,
          compliances: pageable.page.map(IComplianceModel.fromComponentModel),
          paging: pageable.paging
        });
      })
      .catch(_console.error);
  }
}
