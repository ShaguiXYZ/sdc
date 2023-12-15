import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'sdc-trend',
  styleUrl: './sdc-trend.component.scss',
  template: `
    <div class="coverage-trend-background color--{{ trend > 0 ? 'acceptable' : trend < 0 ? 'critical' : 'with_risk' }}"></div>
    <div
      class="coverage-trend sdc-center color--{{ trend > 0 ? 'acceptable' : trend < 0 ? 'critical' : 'with_risk' }}"
      [attr.data-title]="trend"
    >
      <em class="sdc-center fa-regular fa-{{ trend > 0 ? 'face-smile' : trend < 0 ? 'face-frown' : 'face-meh' }}" aria-hidden="true"></em>
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
