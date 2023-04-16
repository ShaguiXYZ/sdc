import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { configContextRoutes } from './core/services/context-data';
import { AppUrls } from './shared/config/routing';

const routes: Routes = [
  { path: AppUrls.root, redirectTo: AppUrls.summary, pathMatch: 'full' },
  {
    path: AppUrls.summary,
    loadChildren: () => import('./modules/sdc-summary/sdc-summary.module').then(m => m.SdcSummaryModule),
    canDeactivate: []
  },
  {
    path: AppUrls.applications,
    loadChildren: () => import('./modules/sdc-applications/sdc-applications.module').then(m => m.SdcApplicationsModule),
    canDeactivate: []
  },
  {
    path: AppUrls.metrics,
    loadChildren: () => import('./modules/sdc-metrics/sdc-metrics.module').then(m => m.SdcMetricsModule),
    canDeactivate: [] }
];

@NgModule({
  imports: [RouterModule.forRoot(configContextRoutes(routes), { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule {}
