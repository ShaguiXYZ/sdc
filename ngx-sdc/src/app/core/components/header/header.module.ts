import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxContextMenuModule } from '@aposin/ng-aquila/context-menu';
import { NxHeaderModule } from '@aposin/ng-aquila/header';
import { NxIconModule } from '@aposin/ng-aquila/icon';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { NxRadioToggleModule } from '@aposin/ng-aquila/radio-toggle';
import { NxTooltipModule } from '@aposin/ng-aquila/tooltip';
import { TranslateModule } from '@ngx-translate/core';
import { UiHeaderComponent } from '.';
import { routes } from '../../../modules/sdc-home/sdc-home-routing.module';

@NgModule({
  declarations: [UiHeaderComponent],
  imports: [
    CommonModule,
    NxButtonModule,
    NxContextMenuModule,
    NxHeaderModule,
    NxIconModule,
    NxLinkModule,
    NxRadioToggleModule,
    NxTooltipModule,
    RouterModule.forChild(routes),
    TranslateModule
  ],
  exports: [UiHeaderComponent]
})
export class UiHeaderModule {}
