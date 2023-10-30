import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SdcLazyLoaderComponent } from './sdc-lazy-loader.component';

@NgModule({
  declarations: [SdcLazyLoaderComponent],
  imports: [CommonModule],
  exports: [SdcLazyLoaderComponent]
})
export class SdcLazyLoaderModule {}
