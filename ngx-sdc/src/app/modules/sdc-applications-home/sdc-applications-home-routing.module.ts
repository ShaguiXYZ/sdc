import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SdcApplicationsHomeComponent } from './sdc-applications-home.component';

export const routes: Routes = [
  {
    path: '',
    component: SdcApplicationsHomeComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class SdcApplicationsRoutingModule {}
