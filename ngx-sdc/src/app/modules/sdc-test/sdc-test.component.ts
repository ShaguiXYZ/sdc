import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ISquadModel } from 'src/app/core/models';
import { SdcTestService } from './sdc-test.service';

@Component({
  selector: 'sdc-test',
  templateUrl: './sdc-test.component.html',
  styleUrls: ['./sdc-test.component.scss'],
  providers: [SdcTestService]
})
export class SdcTestPageComponent implements OnInit, OnDestroy {
  public selectOptionValue = 'Select';
  public form!: FormGroup;
  public squads: ISquadModel[] = [];

  private pattern = '^((?!' + this.selectOptionValue + ').)*$';
  private valueChanges$!: Subscription;

  constructor(private fb: FormBuilder, private componentService: SdcTestService) {}

  ngOnInit(): void {
    this.createForm();
    this.loadData();

    this.valueChanges$ = this.form.valueChanges.subscribe(event => {
      this.componentsBySquad(event.squadId);
      console.log(event);
    });
  }

  ngOnDestroy(): void {
    this.valueChanges$.unsubscribe();
  }

  private loadData(): void {
    this.componentService.availableSquads().then(data => {
      this.squads = data.page;
    });
  }

  private componentsBySquad(squadId: number) {
    this.componentService.componentsBySquad(squadId).then(data => console.log(data));
  }

  private createForm(): void {
    this.form = this.fb.group({
      squadId: [null, [Validators.required, Validators.pattern(this.pattern)]]
    });
  }
}
