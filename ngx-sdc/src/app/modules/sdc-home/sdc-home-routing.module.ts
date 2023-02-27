import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SdcHomePageComponent } from './sdc-home.component';

export const routes: Routes = [
  {
    path: '',
    component: SdcHomePageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class SdcHomePageRoutingModule {}
