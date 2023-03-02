import { EventEmitter, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Languages } from 'src/app/shared/config/app.constants';
import { UiAppContextData } from './context-data.service';

@Injectable({
  providedIn: 'root'
})
export class UiLanguageService {
  private languageChange$: EventEmitter<Languages> = new EventEmitter();

  constructor(private contextData: UiAppContextData) {}

  public i18n(language: Languages): void {
    this.contextData.appConfig = { ...this.contextData.appConfig, lang: language };
    this.languageChange$.emit(this.contextData.appConfig.lang);
  }

  public asObservable(): Observable<Languages> {
    return this.languageChange$.asObservable();
  }
}
