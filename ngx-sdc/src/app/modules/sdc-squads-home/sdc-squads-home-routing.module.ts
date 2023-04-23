import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SdcSquadsHomeComponent } from './sdc-squads-home.component';

export const routes: Routes = [
  {
    path: '',
    component: SdcSquadsHomeComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class SdcSquadsHomeRoutingModule {}
