import { Injectable } from '@angular/core';
import { UiContextDataService } from './context-data.service';
import { CookieService } from 'ngx-cookie-service';
import { IContextData, cookieName } from './models';

/**
 * Cookies
 */
@Injectable({
  providedIn: 'root'
})
export class UiCookieService {
  constructor(private contextData: UiContextDataService, private cookieService: CookieService) {}

  public create(key: string): void {
    const name = cookieName(key);
    const cookieData: IContextData = {
      data: this.contextData.getContextData(key),
      configuration: this.contextData.getContextConfiguration(key)
    };

    this.cookieService.set(name, JSON.stringify(cookieData));

    console.log('Create cookie data', key);
  }

  public retrieve(key: string): void {
    const name = cookieName(key);

    if (this.cookieService.check(name)) {
      const cookie: IContextData = JSON.parse(this.cookieService.get(name));

      if (cookie) {
        this.contextData.setContextData(key, cookie.data, cookie.configuration);

        console.log('Retrieve cookie data', cookie);
      }
    }
  }

  public merge(key: string): void {
    const name = cookieName(key);

    if (this.cookieService.check(name)) {
      const cookie: IContextData = JSON.parse(this.cookieService.get(name));
      const data = this.contextData.getContextData(key);

      if (cookie) {
        this.contextData.setContextData({ ...cookie.data, ...data }, cookie.configuration);
      }
    }
  }
}
