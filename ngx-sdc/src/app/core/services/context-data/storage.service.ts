import { Injectable } from '@angular/core';
import { UiContextDataService } from './context-data.service';
import { IContextData, storageKey } from './models';

/**
 * Cookies
 */
@Injectable({
  providedIn: 'root'
})
export class UiStorageService {
  constructor(private contextData: UiContextDataService) {}

  public create(key: string): void {
    const name = storageKey(key);
    const storageData: IContextData = {
      data: this.contextData.getContextData(key),
      configuration: this.contextData.getContextConfiguration(key)
    };

    localStorage.setItem(name, JSON.stringify(storageData));

    console.log('Create storage data', key);
  }

  public retrieve(key: string): void {
    const name = storageKey(key);

    const storageData = localStorage.getItem(name);
    if (storageData) {
      const contextData: IContextData = JSON.parse(storageData);

      if (contextData) {
        this.contextData.setContextData(key, contextData.data, contextData.configuration);

        console.log('Retrieve storage data', contextData);
      }
    }
  }

  public merge(key: string): void {
    const name = storageKey(key);

    const data = localStorage.getItem(name);
    if (data) {
      const storageData: IContextData = JSON.parse(data);
      const contextData = this.contextData.getContextData(key);

      if (storageData) {
        this.contextData.setContextData({ ...contextData, ...storageData }, storageData.configuration);
      }
    }
  }
}
