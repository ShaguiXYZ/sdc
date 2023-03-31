import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxBadgeModule } from '@aposin/ng-aquila/badge';
import { NxCardModule } from '@aposin/ng-aquila/card';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { NxProgressbarModule } from '@aposin/ng-aquila/progressbar';
import { TranslateModule } from '@ngx-translate/core';
import { SdcComplianceBarCardComponent } from './sdc-compliance-bar-card.component';

@NgModule({
  declarations: [SdcComplianceBarCardComponent],
  imports: [CommonModule, NxBadgeModule, NxCardModule, NxGridModule, NxLinkModule, NxProgressbarModule, TranslateModule],
  exports: [SdcComplianceBarCardComponent]
})
export class SdcComplianceBarCardModule {}
