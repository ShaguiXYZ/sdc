import { Injectable } from '@angular/core';
import { UiLanguageService } from '../language';

@Injectable({
  providedIn: 'root'
})
export class UiDateService {
  constructor(private readonly languageService: UiLanguageService) {}

  public dateFormat = (timestamp: number): string => new Intl.DateTimeFormat(this.languageService.getLang()).format(new Date(timestamp));
}
