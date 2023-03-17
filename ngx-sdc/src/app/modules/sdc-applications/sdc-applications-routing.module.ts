import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SdcApplicationsComponent } from './sdc-applications.component';

export const routes: Routes = [
  {
    path: '',
    component: SdcApplicationsComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class SdcApplicationsRoutingModule {}
