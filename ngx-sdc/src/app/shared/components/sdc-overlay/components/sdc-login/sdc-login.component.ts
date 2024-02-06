import { AfterViewInit, Component, Input } from '@angular/core';
import { OverlayItemStatus } from '../../models';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import $ from 'src/app/core/lib/dom.lib';

@Component({
  selector: 'sdc-login',
  template: `
    <section class="sdc-login  {{ state }}">
      <article class="sdc-login__container">
        <header class="sdc-login__header">
          <h1 class="sdc-login__title">{{ 'Label.Login' | translate }}</h1>
        </header>
        <section class="sdc-login__body">
          <form class="sdc-login__form">
            <div class="sdc-login__form-group">
              <label class="sdc-login__label" for="username">{{ 'Label.Username' | translate }}</label>
              <input class="sdc-login__input" type="text" id="username" name="username" />
            </div>
            <div class="sdc-login__form-group">
              <label class="sdc-login__label" for="password">{{ 'Label.Password' | translate }}</label>
              <input class="sdc-login__input" type="password" id="password" name="password" />
            </div>
            <button class="sdc-login__button" type="submit">{{ 'Label.Login' | translate }}</button>
          </form>
        </section>
      </article>
    </section>
  `,
  styleUrls: ['./sdc-login.component.scss'],
  standalone: true,
  imports: [CommonModule, TranslateModule]
})
export class SdcLoginComponent {
  public _state: OverlayItemStatus = 'closed';

  public get state(): string {
    return this._state;
  }
  @Input()
  public set state(value: OverlayItemStatus) {
    this._state = value;

    if (value === 'open') {
      this.focusFirstInput();
    }
  }

  private focusFirstInput(): void {
    // focus on the first input
    const input = $('.sdc-login__input');
    input?.focus();
  }
}
