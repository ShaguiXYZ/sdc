import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'sdc-no-data',
  template: `
    <div class="sdc-center">
      <img title="No results" alt="No results" src="assets/images/woman-with-sunhat.svg" />
    </div>
  `,
  styles: [
    `
      div {
        height: 100%;
        width: 100%;
      }
    `
  ],
  standalone: true,
  imports: [CommonModule]
})
export class SdcNoDataComponent {}
