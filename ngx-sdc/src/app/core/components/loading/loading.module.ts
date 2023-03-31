import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxModalModule } from '@aposin/ng-aquila/modal';
import { NxSpinnerModule } from '@aposin/ng-aquila/spinner';
import { UiLoadingComponent } from './loading.component';

@NgModule({
  declarations: [UiLoadingComponent],
  imports: [CommonModule, NxModalModule, NxSpinnerModule],
  exports: [UiLoadingComponent]
})
export class UiLoadingModule {}
