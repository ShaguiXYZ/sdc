import { Injectable } from '@angular/core';
import { LanguageService } from '../language';

@Injectable({
  providedIn: 'root'
})
export class DateService {
  constructor(private readonly languageService: LanguageService) {}

  public dateFormat = (timestamp: number): string => new Intl.DateTimeFormat(this.languageService.getLang()).format(new Date(timestamp));
}
