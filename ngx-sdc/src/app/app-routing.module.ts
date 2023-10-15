import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { configContextRoutes } from './core/services/context-data';
import { AppUrls } from './shared/config/routing';

const routes: Routes = configContextRoutes([
  { path: AppUrls.root, redirectTo: AppUrls.squads, pathMatch: 'full' },
  {
    path: AppUrls.departments,
    loadChildren: () => import('./modules/sdc-departments-home/sdc-departments-home.module').then(m => m.SdcDepartmentsHomeModule),
    data: { animation: 'departmentsPage' },
    canDeactivate: []
  },
  {
    path: AppUrls.squads,
    loadChildren: () => import('./modules/sdc-squads-home/sdc-squads-home.module').then(m => m.SdcSquadsHomeModule),
    data: { animation: 'squadsPage' },
    canDeactivate: []
  },
  {
    path: AppUrls.applications,
    loadChildren: () => import('./modules/sdc-applications/sdc-applications.module').then(m => m.SdcApplicationsModule),
    data: { animation: 'applicationsPage' },
    canDeactivate: []
  },
  {
    path: AppUrls.metrics,
    loadChildren: () => import('./modules/sdc-metrics/sdc-metrics.module').then(m => m.SdcMetricsModule),
    data: { animation: 'metricsPage' },
    canDeactivate: []
  },
  {
    path: AppUrls.test,
    loadComponent: () => import('./modules/sdc-test-page/sdc-test-page.component').then(m => m.SdcTestComponent),
    canDeactivate: []
  }
]);

@NgModule({
  imports: [RouterModule.forRoot(routes, {})],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule {}
