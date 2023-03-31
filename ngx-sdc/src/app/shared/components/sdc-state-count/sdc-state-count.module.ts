import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { SdcStateCountComponent } from './sdc-state-count.component';

@NgModule({
  declarations: [SdcStateCountComponent],
  imports: [CommonModule, TranslateModule],
  exports: [SdcStateCountComponent]
})
export class SdcStateCountModule {}
