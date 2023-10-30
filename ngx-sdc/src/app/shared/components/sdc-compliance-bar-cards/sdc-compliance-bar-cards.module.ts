import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { SdcComplianceBarCardModule } from '../sdc-compliance-bar-card/sdc-compliance-bar-card.module';
import { SdcComplianceBarCardsComponent } from './sdc-compliance-bar-cards.component';

@NgModule({
  declarations: [SdcComplianceBarCardsComponent],
  imports: [CommonModule, SdcComplianceBarCardModule, TranslateModule],
  exports: [SdcComplianceBarCardsComponent]
})
export class SdcComplianceBarCardsModule {}
