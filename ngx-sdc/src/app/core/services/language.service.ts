import { EventEmitter, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UiAppContextDataService } from './context-data/context-data.service';
import { CoreContextDataNames, ICoreConfig } from './context-data/models';

@Injectable({
  providedIn: 'root'
})
export class UiLanguageService {
  private languageChange$: EventEmitter<string> = new EventEmitter();

  constructor(private contextData: UiAppContextDataService) {}

  public i18n(language: string): void {
    const appConfig = this.appConfig();
    this.contextData.setContextData(CoreContextDataNames.appConfig, { ...appConfig, lang: language });
    this.languageChange$.emit(this.appConfig().lang);
  }

  public asObservable(): Observable<string> {
    return this.languageChange$.asObservable();
  }

  private appConfig(): ICoreConfig {
    return this.contextData.contextDataServiceConfiguration();
  }
}
