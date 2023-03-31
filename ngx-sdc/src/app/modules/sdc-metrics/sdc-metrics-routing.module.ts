import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SdcMetricsComponent } from './sdc-metrics.component';

export const routes: Routes = [
  {
    path: '',
    component: SdcMetricsComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class SdcMetricsRoutingModule {}
