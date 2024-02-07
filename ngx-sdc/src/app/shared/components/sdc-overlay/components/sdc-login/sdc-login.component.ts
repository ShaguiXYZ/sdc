import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { OverlayItemStatus } from '../../models';
import { SdcLoginService } from './services';

@Component({
  selector: 'sdc-login',
  template: `
    <section class="sdc-login  {{ state }}">
      <article class="sdc-login__container">
        <header class="sdc-login__header">
          <h1 class="sdc-login__title">{{ 'Label.Login' | translate }}</h1>
        </header>
        <section class="sdc-login__body">
          <form class="sdc-login__form" [formGroup]="form" (submit)="login()">
            <div class="sdc-login__form-group">
              <label class="sdc-login__label" for="userName">{{ 'Label.userName' | translate }}</label>
              <input class="sdc-login__input" type="text" id="userName" name="userName" formControlName="userName" />
              @if (form.get('userName')?.invalid && form.get('userName')?.touched) {
                <div class="sdc-login__error">
                  {{ 'Error.Username' | translate }}
                </div>
              }
            </div>
            <div class="sdc-login__form-group">
              <label class="sdc-login__label" for="password">{{ 'Label.Password' | translate }}</label>
              <input class="sdc-login__input" type="password" id="password" name="password" formControlName="password" />
              @if (form.get('password')?.invalid && form.get('password')?.touched) {
                <div class="sdc-login__error">
                  {{ 'Error.Password' | translate }}
                </div>
              }
            </div>
            <button class="sdc-login__button" type="submit">{{ 'Label.Login' | translate }}</button>
          </form>
        </section>
      </article>
    </section>
  `,
  providers: [SdcLoginService],
  styleUrls: ['./sdc-login.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, TranslateModule]
})
export class SdcLoginComponent implements OnInit {
  public form!: FormGroup;
  public _state: OverlayItemStatus = 'closed';

  constructor(private readonly loginService: SdcLoginService) {}

  ngOnInit(): void {
    this.createForm();
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
