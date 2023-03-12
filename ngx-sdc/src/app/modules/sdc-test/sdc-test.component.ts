import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ISquadModel } from 'src/app/core/models';
import { ISquadInfo, SdcTestService } from './sdc-test.service';

@Component({
  selector: 'sdc-test',
  templateUrl: './sdc-test.component.html',
  styleUrls: ['./sdc-test.component.scss']
})
export class SdcTestPageComponent implements OnInit, OnDestroy {
  public selectOptionValue = 'Select';
  public form!: FormGroup;
  public squads: ISquadModel[] = [];
  public squadInfo!: ISquadInfo;

  private pattern = `^((?!${  this.selectOptionValue  }).)*$`;
  private subscription$: Array<Subscription> = [];

  constructor(private fb: FormBuilder, private sdcTestService: SdcTestService) {}

  ngOnInit(): void {
    this.createForm();
    this.loadData();

    this.subscription$.push(this.form.valueChanges.subscribe(event => {
      this.componentsBySquad(event.squadId);
    }));

    this.subscription$.push(this.sdcTestService.onDataChange().subscribe(info => {
      this.squadInfo = info;
    }));
  }

  ngOnDestroy(): void {
    this.subscription$.forEach(subscription => subscription.unsubscribe());
  }

  private loadData(): void {
    this.sdcTestService.availableSquads().then(data => {
      this.squads = data.page;
    });
  }

  private componentsBySquad(squadId: number) {
    this.sdcTestService.squadInfo(squadId);
  }

  private createForm(): void {
    this.form = this.fb.group({
      squadId: [null, [Validators.required, Validators.pattern(this.pattern)]]
    });
  }
}
