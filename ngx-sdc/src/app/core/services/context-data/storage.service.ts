import { Injectable } from '@angular/core';
import { UiContextDataService } from './context-data.service';
import { IContextData, storageKey } from './models';
import { _console } from '../../lib';

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
      data: this.contextData.get(key),
      configuration: this.contextData.getConfiguration(key)
    };

    localStorage.setItem(name, JSON.stringify(storageData));

    _console.log('Created storage data', key);
  }

  public retrieve(key: string): void {
    const name = storageKey(key);

    const storageData = localStorage.getItem(name);
    if (storageData) {
      const contextData: IContextData = JSON.parse(storageData);

      if (contextData) {
        this.contextData.set(key, contextData.data, contextData.configuration);

        _console.log(`Retrieve storage data ${key}`, contextData);
      }
    }
  }

  public merge(key: string): void {
    const name = storageKey(key);

    const data = localStorage.getItem(name);
    if (data) {
      const storageData: IContextData = JSON.parse(data);
      const contextData = this.contextData.get(key);

      if (storageData) {
        this.contextData.set({ ...contextData, ...storageData }, storageData.configuration);
      }
    }
  }
}
