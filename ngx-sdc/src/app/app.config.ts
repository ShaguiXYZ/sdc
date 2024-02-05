import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { RouterModule, provideRouter } from '@angular/router';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { TranslateModule } from '@ngx-translate/core';
import { NgxEchartsModule } from 'ngx-echarts';
import { routes } from './app-routes';
import { NX_HEADER_CONFIG } from './core/components';
import { AuthInterceptor, NX_CONTEX_CONFIG, NX_LANGUAGE_CONFIG } from './core/services';
import { SDC_HEADER_MENU } from './shared/config/menu';
import { AppUrls, urls } from './shared/config/routing';
import { TRANSLATE_MODULE_CONFIG } from './shared/config/translate-utils';
import { SCHEDULER_PERIOD } from './shared/constants';

const SdcLanguages = {
  ['enGB']: 'en-GB',
  ['esES']: 'es-ES'
};

export const appConfig: ApplicationConfig = {
  providers: [
    importProvidersFrom(
      BrowserModule,
      NgxEchartsModule.forRoot({
        echarts: () => import('echarts')
      }),
      NxGridModule,
      RouterModule,
      TranslateModule.forRoot(TRANSLATE_MODULE_CONFIG)
    ),
    { provide: NX_CONTEX_CONFIG, useValue: { urls, home: AppUrls.squads, cache: { schedulerPeriod: SCHEDULER_PERIOD } } },
    { provide: NX_LANGUAGE_CONFIG, useValue: { languages: SdcLanguages } },
    { provide: NX_HEADER_CONFIG, useValue: { logo: 'assets/images/header-logo.svg', navigation: SDC_HEADER_MENU, themeSwitcher: false } },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    provideAnimations(),
    provideHttpClient(withInterceptorsFromDi()),
    provideRouter(routes),
    provideClientHydration()
  ]
};
