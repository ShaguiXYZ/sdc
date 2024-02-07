import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'sdc-key',
  styles: [
    `
      .sdc-key-content {
        display: flex;
        justify-content: center;
        align-items: center;

        .sdc-key {
          background-position: center;
          background-repeat: no-repeat;
          background-size: contain;
          height: 20px;
          width: 50px;
        }

        .sdc-key-label {
          font-size: 1.5rem;
          font-weight: bold;
          margin-left: 1rem;
        }
      }
    `
  ],
  template: `
    <div class="sdc-key-content">
      <div class="sdc-key" [style.backgroundImage]="svgKey"></div>
      <span class="sdc-key-label">{{ label | translate }}</span>
    </div>
  `,
  standalone: true,
  imports: [CommonModule, TranslateModule]
})
export class SdcKeyComponent {
  public svgKey!: string;

  @Input()
  public set key(value: string) {
    this.svgKey = value && `url('assets/images/ctrl-${value.toLowerCase()}.svg')`;
  }

  @Input()
  public label!: string;
}
