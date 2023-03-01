import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SdcTestPageComponent } from './sdc-test.component';

export const routes: Routes = [
  {
    path: '',
    component: SdcTestPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class SdcTestPageRoutingModule {}
