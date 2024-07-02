import { inject, Injectable } from '@angular/core';
import { LanguageService } from '../language';

@Injectable({
  providedIn: 'root'
})
export class DateService {
  private readonly languageService = inject(LanguageService);

  public dateFormat = (timestamp: number): string => new Intl.DateTimeFormat(this.languageService.getLang()).format(new Date(timestamp));
}
