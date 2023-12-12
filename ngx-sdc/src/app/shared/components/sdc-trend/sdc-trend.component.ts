import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'sdc-trend',
  styles: [
    `
      @import 'colors';

      // Defines a custom property
      @mixin define-custom-property($name, $value) {
        #{custom-property-color($name)} {
          color: $value;
        }
      }

      @each $name, $value in $coverage-colors {
        @include define-custom-property($name, $value);
      }

      .coverage-trend {
        border: 2px dashed;
        border-radius: 100%;
        height: 22px;
        position: relative;
        width: 22px;

        em {
          width: calc(100% - 6px);
          height: calc(100% - 6px);
        }
      }
    `
  ],
  template: `
    <div class="coverage-trend sdc-center color--{{ trend > 0 ? 'acceptable' : trend < 0 ? 'critical' : 'with_risk' }}">
      <em class="sdc-center fa-solid fa-arrow-{{ trend > 0 ? 'up' : trend < 0 ? 'down' : 'right' }}" aria-hidden="true"></em>
    </div>
  `,
  standalone: true,
  imports: [CommonModule]
})
export class SdcTrendComponent {
  @Input()
  public trend!: number;
}
