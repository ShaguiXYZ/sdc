import { CommonModule } from '@angular/common';
import { NgModule, Optional, SkipSelf } from '@angular/core';

@NgModule({
  imports: [CommonModule],
  providers: []
})
export class AppCoreModule {
  constructor(@Optional() @SkipSelf() parentModule: AppCoreModule) {
    if (parentModule) {
      throw new Error(`AppCoreModule has already been loaded. Import Core modules in the AppModule only.`);
    }
  }
}
