import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxBreadcrumbModule } from '@aposin/ng-aquila/breadcrumb';
import { SdcBreadcrumbComponent } from './sdc-breadcrumb.component';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  declarations: [SdcBreadcrumbComponent],
  imports: [NxBreadcrumbModule, CommonModule, TranslateModule],
  exports: [SdcBreadcrumbComponent]
})
export class SdcBreadcrumbModule {}
