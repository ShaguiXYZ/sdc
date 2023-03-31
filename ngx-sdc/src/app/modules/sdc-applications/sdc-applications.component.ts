import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ISquadModel } from 'src/app/core/models/sdc';
import { UiContextDataService } from 'src/app/core/services';
import { IComplianceModel } from 'src/app/shared/components';
import { ContextDataInfo } from 'src/app/shared/constants/context-data';
import { SdcApplicationsModel } from './models';
import { SdcApplicationsService } from './services';
import { Router } from '@angular/router';
import { AppUrls } from 'src/app/shared/config/routing';

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

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private contextDataService: UiContextDataService,
    private sdcApplicationsService: SdcApplicationsService
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.loadData();

    this.subscription$.push(
      this.form.valueChanges.subscribe(event => {
        this.sdcApplicationsService.squadData(event.squadId, this.page);
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

  public complianceClicked(compliance: IComplianceModel) {
    this.contextDataService.setContextData(ContextDataInfo.METRICS_DATA, { compliance });
    this.router.navigate([AppUrls.metrics]);
  }

  private loadData(): void {
    this.sdcApplicationsService.availableSquads().then(data => {
      this.squads = data.page;
    });
  }

  private createForm(): void {
    this.form = this.fb.group({
      squadId: [this.sdcApplicationsService.contextData?.squad, [Validators.required, Validators.pattern(this.pattern)]]
    });
  }
}
