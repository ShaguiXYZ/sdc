import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { TranslateModule } from '@ngx-translate/core';
import { SdcNoDataComponent } from './sdc-no-data.component';

@NgModule({
  declarations: [SdcNoDataComponent],
  imports: [CommonModule, NxHeadlineModule, TranslateModule],
  exports: [SdcNoDataComponent]
})
export class SdcNoDataModule {}
