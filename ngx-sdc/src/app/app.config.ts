import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { ApplicationConfig, importProvidersFrom, inject, provideAppInitializer } from '@angular/core';
import { provideClientHydration, withNoHttpTransferCache } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { ContextDataService, NX_CONTEX_CONFIG } from '@shagui/ng-shagui/core';
import { routes } from './app.routes';
import { NX_HEADER_CONFIG } from './core/components';
import { APP_NAME } from './core/constants';
import { IAppConfigurationModel } from './core/models/sdc';
import { authInterceptor, NX_LANGUAGE_CONFIG } from './core/services';
import { AppConfigurationService } from './core/services/sdc/app-configuration.service';
import { SDC_HEADER_MENU } from './shared/config/menu';
import { AppUrls, urls } from './shared/config/routing';
import { TRANSLATE_MODULE_CONFIG } from './shared/config/translate-utils';
import { ContextDataInfo, SCHEDULER_PERIOD } from './shared/constants';

const SdcLanguages = {
  ['enGB']: 'en-GB',
  ['esES']: 'es-ES'
};

const appInitializer = async (): Promise<void> => {
  const appConfiguration = inject(AppConfigurationService);
  const contextDataService = inject(ContextDataService);

  const config = await appConfiguration.appConfiguracions();

  contextDataService.set<IAppConfigurationModel>(
    ContextDataInfo.APP_CONFIG,
    { ...config, title: '- S D C -' },
    { persistent: true, referenced: false }
  );
};

export const appConfig: ApplicationConfig = {
  providers: [
    importProvidersFrom(TranslateModule.forRoot(TRANSLATE_MODULE_CONFIG)),
    provideAppInitializer(appInitializer),
    provideAnimations(),
    provideHttpClient(withInterceptors([authInterceptor])),
    provideRouter(routes),
    provideClientHydration(
      /**
       * @howto prevent ssr caching of http requests
       *
       * ref: https://angular.io/guide/ssr#caching-http-requests
       */
      withNoHttpTransferCache()
      // withHttpTransferCacheOptions({
      //   filter: req => req.method === 'GET'
      // })
    ),
    // @howto initialize the app configuration
    // {
    //   provide: APP_INITIALIZER,
    //   useFactory: iniSettings,
    //   deps: [AppConfigurationService, ContextDataService],
    //   multi: true
    // },
    {
      provide: NX_CONTEX_CONFIG,
      useValue: { appName: APP_NAME.toUpperCase(), urls, home: AppUrls.squads, cache: { schedulerPeriod: SCHEDULER_PERIOD } }
    },
    { provide: NX_LANGUAGE_CONFIG, useValue: { languages: SdcLanguages } },
    { provide: NX_HEADER_CONFIG, useValue: { logo: 'assets/images/header-logo.svg', navigation: SDC_HEADER_MENU, themeSwitcher: false } }
  ]
};
