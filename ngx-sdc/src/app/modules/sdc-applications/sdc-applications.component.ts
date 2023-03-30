import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ISquadModel } from 'src/app/core/models/sdc';
import { UiContextDataService } from 'src/app/core/services';
import { ApplicationsContextData, ContextDataInfo } from 'src/app/shared/constants/context-data';
import { SdcApplicationsModel } from './models';
import { SdcApplicationsService } from './services';

@Component({
  selector: 'sdc-applications',
  templateUrl: './sdc-applications.component.html',
  styleUrls: ['./sdc-applications.component.scss'],
  providers: [SdcApplicationsService]
})
export class SdcApplicationsComponent implements OnInit, OnDestroy {
  public selectOptionValue = 'Select';
  public form!: FormGroup;
  public squads: ISquadModel[] = [];
  public applicationsInfo?: SdcApplicationsModel;
  public page = 0;

  private pattern = `^((?!${this.selectOptionValue}).)*$`;
  private subscription$: Array<Subscription> = [];
  private contextData?: ApplicationsContextData;

  constructor(
    private fb: FormBuilder,
    private sdcApplicationsService: SdcApplicationsService,
    private contextDataService: UiContextDataService
  ) {}

  ngOnInit(): void {
    this.contextData = this.contextDataService.getContextData(ContextDataInfo.APPLICATIONS_DATA);

    this.createForm();
    this.loadData();

    this.subscription$.push(
      this.form.valueChanges.subscribe(event => {
        this.sdcApplicationsService.squadData(event.squadId, this.page);
        this.contextDataService.setContextData(
          ContextDataInfo.APPLICATIONS_DATA,
          { ...this.contextData, squad: event.squadId },
          { persistent: true, referenced: true }
        );
      })
    );

    this.subscription$.push(
      this.sdcApplicationsService.onDataChange().subscribe(info => {
        this.applicationsInfo = info;
      })
    );
  }

  ngOnDestroy(): void {
    this.subscription$.forEach(subscription => subscription.unsubscribe());
  }

  private loadData(): void {
    this.sdcApplicationsService.availableSquads().then(data => {
      this.squads = data.page;
    });
  }

  private createForm(): void {
    this.form = this.fb.group({
      squadId: [this.contextData?.squad, [Validators.required, Validators.pattern(this.pattern)]]
    });
  }
}
