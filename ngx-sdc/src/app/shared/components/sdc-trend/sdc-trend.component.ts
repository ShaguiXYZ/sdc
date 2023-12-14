import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'sdc-trend',
  styleUrl: './sdc-trend.component.scss',
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
