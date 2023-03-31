import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SdcStateCountModule } from '../sdc-state-count/sdc-state-count.module';
import { SdcComponentsStateCountComponent } from './sdc-components-state-count.component';

@NgModule({
  declarations: [SdcComponentsStateCountComponent],
  imports: [CommonModule, SdcStateCountModule],
  exports: [SdcComponentsStateCountComponent]
})
export class SdcComponentsStateCountModule {}
