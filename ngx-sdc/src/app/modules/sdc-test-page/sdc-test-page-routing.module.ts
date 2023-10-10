import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SdcTestComponent } from './sdc-test-page.component';

export const routes: Routes = [
  {
    path: '',
    component: SdcTestComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class SdcTestRoutingModule {}
