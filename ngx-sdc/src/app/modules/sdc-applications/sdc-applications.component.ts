import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ISquadModel } from 'src/app/core/models/sdc';
import { SdcApplicationsModel } from './models';
import { SdcApplicationsService } from './services';

@Component({
  selector: 'sdc-test',
  templateUrl: './sdc-applications.component.html',
  styleUrls: ['./sdc-applications.component.scss'],
  providers: [SdcApplicationsService]
})
export class SdcApplicationsComponent implements OnInit, OnDestroy {
  public selectOptionValue = 'Select';
  public form!: FormGroup;
  public squads: ISquadModel[] = [];
  public applicationsInfo?: SdcApplicationsModel;

  private pattern = `^((?!${this.selectOptionValue}).)*$`;
  private subscription$: Array<Subscription> = [];

  constructor(private fb: FormBuilder, private sdcApplicationsService: SdcApplicationsService) {}

  ngOnInit(): void {
    this.createForm();
    this.loadData();

    this.subscription$.push(
      this.form.valueChanges.subscribe(event => {
        this.sdcApplicationsService.squadData(event.squadId);
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
      squadId: [null, [Validators.required, Validators.pattern(this.pattern)]]
    });
  }
}
