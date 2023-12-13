import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'sdc-trend',
  styles: [
    `
      @import 'colors';
      @import 'core-globals';

      // Defines a custom property
      @mixin define-custom-property($name, $value) {
        #{custom-property-color($name)} {
          color: $value;
        }
      }

      @each $name, $value in $coverage-colors {
        @include define-custom-property($name, $value);
      }

      :host {
        position: relative;
      }

      [data-title]:hover:after {
        opacity: 0.8;
        visibility: visible;
      }

      [data-title]:after {
        content: attr(data-title);
        background-color: var(--ui-06);
        border: 1px solid var(--ui-04);
        border-radius: 15px;
        box-shadow: 1px 1px 3px var(--ui-06);
        color: var(--ui-01);
        font-size: 12px;
        opacity: 0;
        padding: 4px 10px;
        position: absolute;
        right: 0;
        transition: all 0.5s ease 0.3s;
        visibility: hidden;
        white-space: nowrap;
        z-index: $z-index-bottom;
      }

      [data-title] {
        position: relative;
      }

      .coverage-trend {
        border: 2px dashed;
        border-radius: 100%;
        height: 22px;
        width: 22px;

        em {
          height: calc(100% - 6px);
          position: absolute;
          width: calc(100% - 6px);
        }
      }
    `
  ],
  template: `
    <div
      class="coverage-trend sdc-center color--{{ trend > 0 ? 'acceptable' : trend < 0 ? 'critical' : 'with_risk' }}"
      [attr.data-title]="trend"
    >
      <em class="sdc-center fa-solid fa-arrow-{{ trend > 0 ? 'up' : trend < 0 ? 'down' : 'right' }}" aria-hidden="true"></em>
    </div>
  `,
  standalone: true,
  imports: [CommonModule]
})
export class SdcTrendComponent {
  private _trend!: number;

  @Input()
  public set trend(value: number) {
    this._trend = Math.round(value * 100) / 100;
  }
  public get trend(): number {
    return this._trend;
  }
}
