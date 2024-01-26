import { Routes } from '@angular/router';
import { configContextRoutes } from './core/services';
import { AppUrls } from './shared/config/routing';

export const routes: Routes = configContextRoutes([
  { path: AppUrls.root, redirectTo: AppUrls.squads, pathMatch: 'full' },
  {
    path: AppUrls.departments,
    loadComponent: () => import('./modules/sdc-departments-home/sdc-departments-home.component').then(c => c.SdcDepartmentsHomeComponent),
    data: { animation: AppUrls.departments, help: AppUrls.departments },
    canDeactivate: []
  },
  {
    path: AppUrls.squads,
    loadComponent: () => import('./modules/sdc-squads-home/sdc-squads-home.component').then(c => c.SdcSquadsHomeComponent),
    data: { animation: AppUrls.squads, help: AppUrls.squads },
    canDeactivate: []
  },
  {
    path: AppUrls.applications,
    loadComponent: () =>
      import('./modules/sdc-applications-home/sdc-applications-home.component').then(c => c.SdcApplicationsHomeComponent),
    data: { animation: AppUrls.applications, help: AppUrls.applications },
    canDeactivate: []
  },
  {
    path: AppUrls.metrics,
    loadComponent: () => import('./modules/sdc-metrics-home/sdc-metrics-home.component').then(c => c.SdcMetricsHomeComponent),
    data: { animation: AppUrls.metrics },
    canDeactivate: []
  },
  {
    path: AppUrls.test,
    loadComponent: () => import('./modules/sdc-test-page/sdc-test-page.component').then(c => c.SdcTestComponent),
    data: { animation: AppUrls.test },
    canDeactivate: []
  },
  {
    path: AppUrls.routing,
    loadComponent: () => import('./modules/sdc-routing/sdc-routing').then(c => c.SdcRoutingComponent),
    canDeactivate: []
  }
]);
