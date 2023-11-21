import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { enableProdMode, importProvidersFrom } from '@angular/core';
import { BrowserModule, bootstrapApplication } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { RouterModule, provideRouter } from '@angular/router';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { TranslateModule } from '@ngx-translate/core';
import { NgxEchartsModule } from 'ngx-echarts';
import { routes } from './app/app-routes';
import { AppComponent } from './app/app.component';
import { NX_HEADER_CONFIG } from './app/core/components/header';
import { NX_CONTEX_CONFIG } from './app/core/services/context-data';
import { NX_LANGUAGE_CONFIG } from './app/core/services/language';
import { AuthInterceptor } from './app/core/services/security';
import { SDC_HEADER_MENU } from './app/shared/config/menu';
import { AppUrls, urls } from './app/shared/config/routing';
import { TRANSLATE_MODULE_CONFIG } from './app/shared/config/translate-utils';
import { SCHEDULER_PERIOD } from './app/shared/constants';
import { environment } from './environments/environment';

const SdcLanguages = {
  ['enGB']: 'en-GB',
  ['esES']: 'es-ES'
};

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(AppComponent, {
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
    { provide: NX_HEADER_CONFIG, useValue: { navigation: SDC_HEADER_MENU } },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    provideAnimations(),
    provideHttpClient(withInterceptorsFromDi()),
    provideRouter(routes)
  ]
}).catch(err => console.error(err));
