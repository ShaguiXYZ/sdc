import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxSpinnerModule } from '@aposin/ng-aquila/spinner';
import { UiLoadingComponent } from './loading.component';

@NgModule({
  declarations: [UiLoadingComponent],
  imports: [CommonModule, NxSpinnerModule],
  exports: [UiLoadingComponent]
})
export class UiLoadingModule {}
