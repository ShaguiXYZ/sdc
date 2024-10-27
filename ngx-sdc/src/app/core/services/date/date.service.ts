import { inject, Injectable } from '@angular/core';
import { NxDate } from '@shagui/ng-shagui/core';
import { LanguageService } from '../language';

@Injectable({
  providedIn: 'root'
})
export class DateService {
  private readonly languageService = inject(LanguageService);

  public format = (timestamp: number): string => new NxDate(timestamp).localized(this.languageService.getLang());
}
