import { EventEmitter, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Languages } from 'src/app/core/constants/languages';
import { AppConfig } from './context-data/models/app-config.model';
import { UiAppContextDataService } from './context-data/context-data.service';
import { CoreContextDataNames } from './context-data/models';

@Injectable({
  providedIn: 'root'
})
export class UiLanguageService {
  private languageChange$: EventEmitter<Languages> = new EventEmitter();

  constructor(private contextData: UiAppContextDataService) {}

  public i18n(language: Languages): void {
    const appConfig = this.appConfig();
    this.contextData.setContextData(CoreContextDataNames.appConfig, { ...appConfig, lang: language });
    this.languageChange$.emit(this.appConfig().lang);
  }

  public asObservable(): Observable<Languages> {
    return this.languageChange$.asObservable();
  }

  private appConfig(): AppConfig {
    return this.contextData.getContextData(CoreContextDataNames.appConfig) as AppConfig;
  }
}
