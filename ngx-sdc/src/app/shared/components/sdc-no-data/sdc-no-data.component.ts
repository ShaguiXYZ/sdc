import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'sdc-no-data',
  styles: [
    `
      div {
        background-image: url('/assets/images/woman-with-sunhat.svg');
        background-position: center;
        background-repeat: no-repeat;
        background-size: contain;
        height: 100%;
        width: 100%;
      }
    `
  ],
  template: ` <div class="no-data sdc-center"></div> `,
  standalone: true,
  imports: [CommonModule]
})
export class SdcNoDataComponent {}
