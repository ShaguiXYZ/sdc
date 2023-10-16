import { Pipe, PipeTransform } from '@angular/core';
import { isNumeric } from 'src/app/core/lib';
import { ValueType } from 'src/app/core/models/sdc';

@Pipe({
  name: 'sdcValueTypeToNumber'
})
export class SdcValueTypeToNumberPipe implements PipeTransform {
  private DEC_SUB = 3;

  transform(value?: string, type: ValueType = ValueType.NUMERIC): number | undefined {
    switch (type) {
      case ValueType.NUMERIC:
        return this.numericValue(value);
      case ValueType.BOOLEAN:
        return this.booleanValue(value);
      case ValueType.VERSION:
        return this.versionValue(value);
      default:
        return undefined;
    }
  }

  private numericValue(value?: string): number | undefined {
    return value && isNumeric(value) ? Number(value) : undefined;
  }

  private booleanValue(value?: string): number | undefined {
    return /true/i.test(value || 'false') ? 1 : 0;
  }

  private versionValue(value: string = ''): number | undefined {
    const [version, ...segments] = value.split('.');
    const integerPart = isNumeric(version) ? version : '0';
    let decimalPart = '';

    segments?.forEach(segment => {
      const padSegment = isNumeric(segment) ? segment.padStart(this.DEC_SUB, '0') : ''.padStart(this.DEC_SUB, '0');
      decimalPart = `${decimalPart}${padSegment}`;
    });

    return Number(`${integerPart}.${decimalPart ?? 0}`);
  }
}
