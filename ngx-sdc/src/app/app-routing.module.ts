import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppUrls } from './shared/config/routing';

const routes: Routes = [
  { path: AppUrls.root, redirectTo: AppUrls.home, pathMatch: 'full' },
  {
    path: AppUrls.home,
    loadChildren: () => import('./modules/sdc-home/sdc-home.module').then(m => m.SdcHomePageModule),
    canDeactivate: [],
    canActivate: []
  },
  {
    path: AppUrls.test,
    loadChildren: () => import('./modules/sdc-test/sdc-test.module').then(m => m.SdcTestPageModule),
    canDeactivate: [],
    canActivate: []
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule {}
