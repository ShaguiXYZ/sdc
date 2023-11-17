import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SdcMetricsHomeComponent } from './sdc-metrics-home.component';

export const routes: Routes = [
  {
    path: '',
    component: SdcMetricsHomeComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class SdcMetricsRoutingModule {}
