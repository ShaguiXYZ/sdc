import { CommonModule } from '@angular/common';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { OverlayItemStatus } from '../../models';
import { SdcLoginService } from './services';
import { Subscription } from 'rxjs';
import { SdcLoginModel } from './models';

@Component({
  selector: 'sdc-login',
  styleUrls: ['./sdc-login.component.scss'],
  templateUrl: './sdc-login.component.html',
  providers: [SdcLoginService],
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, TranslateModule]
})
export class SdcLoginComponent implements OnInit, OnDestroy {
  public loginData: SdcLoginModel = { authenticating: false };
  public form!: FormGroup;
  public _state: OverlayItemStatus = 'closed';

  private subcriptions: Subscription[] = [];

  constructor(private readonly loginService: SdcLoginService) {}

  ngOnInit(): void {
    this.subcriptions.push(
      this.loginService.onDataChange().subscribe(data => {
        this.loginData = data;
      })
    );

    this.createForm();
  }

  ngOnDestroy(): void {
    this.subcriptions.forEach(subscription => subscription.unsubscribe());
  }

  public get state(): string {
    return this._state;
  }
  @Input()
  public set state(value: OverlayItemStatus) {
    this._state = value;
    this.form && this.resetForm();
  }

  public login(): void {
    if (this.form.valid) {
      this.loginData = { authenticating: true };
      this.loginService.login(this.form.value);
    }
  }

  private resetForm(): void {
    this.form.reset();
  }

  private createForm(): void {
    this.form = new FormGroup({
      userName: new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(16)]),
      password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(32)])
    });
  }
}
