import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'helpParagraph',
  standalone: true
})
export class SdcHelpParagraphPipe implements PipeTransform {
  transform(value: string | string[]): string {
    return value instanceof Array ? value.join('') : value;
  }
}
