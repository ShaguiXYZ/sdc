import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SdcLazyComponentLoaderComponent } from './sdc-lazy-component-loader.component';

@NgModule({
  declarations: [SdcLazyComponentLoaderComponent],
  imports: [CommonModule],
  exports: [SdcLazyComponentLoaderComponent]
})
export class SdcLazyComponentLoaderModule {}
