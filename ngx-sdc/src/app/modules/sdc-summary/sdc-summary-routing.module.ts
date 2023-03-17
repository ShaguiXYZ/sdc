import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SdcSummaryComponent } from './sdc-summary.component';

export const routes: Routes = [
  {
    path: '',
    component: SdcSummaryComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class SdcSummaryRoutingModule {}
