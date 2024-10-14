import { inject, Injectable } from '@angular/core';
import { dateFormat } from '@shagui/ng-shagui/core';
import { LanguageService } from '../language';

@Injectable({
  providedIn: 'root'
})
export class DateService {
  private readonly languageService = inject(LanguageService);

  public format = (timestamp: number): string => dateFormat(timestamp, this.languageService.getLang());
}
