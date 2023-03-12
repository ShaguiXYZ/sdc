import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxContextMenuModule } from '@aposin/ng-aquila/context-menu';
import { NxHeaderModule } from '@aposin/ng-aquila/header';
import { NxIconModule } from '@aposin/ng-aquila/icon';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { NxRadioToggleModule } from '@aposin/ng-aquila/radio-toggle';
import { NxTooltipModule } from '@aposin/ng-aquila/tooltip';
import { TranslateModule } from '@ngx-translate/core';
import { NX_HEADER_CONFIG, UiHeaderComponent } from '.';
import { routes } from '../sdc-home/sdc-home-routing.module';
import { DEFAULT_HEADER_MENU } from './navigation.model';

@NgModule({
  declarations: [UiHeaderComponent],
  imports: [
    CommonModule,
    FormsModule,
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
  exports: [UiHeaderComponent],
  providers: [{ provide: NX_HEADER_CONFIG, useValue: { navigation: DEFAULT_HEADER_MENU } }]
})
export class UiHeaderModule {}
