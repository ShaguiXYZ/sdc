import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SdcDepartmentsHomeComponent } from './sdc-departments-home.component';

export const routes: Routes = [
  {
    path: '',
    component: SdcDepartmentsHomeComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class SdcDepartmentsHomeRoutingModule {}
