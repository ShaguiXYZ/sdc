import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { TranslateModule } from '@ngx-translate/core';
import { SdcStateCountComponent } from './sdc-state-count.component';

@NgModule({
  declarations: [SdcStateCountComponent],
  imports: [CommonModule, NxLinkModule, TranslateModule],
  exports: [SdcStateCountComponent]
})
export class SdcStateCountModule {}
