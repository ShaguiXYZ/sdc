import { Pipe, PipeTransform } from '@angular/core';
import { isNumeric } from 'src/app/core/lib';
import { ValueType } from 'src/app/core/models/sdc';

@Pipe({
  name: 'sdcValueTypeToNumber'
})
export class SdcValueTypeToNumberPipe implements PipeTransform {
  transform(value?: string, type: ValueType = ValueType.NUMERIC): number | undefined {
    switch (type) {
      case ValueType.NUMERIC:
        return this.numericValue(value);
      case ValueType.VERSION:
        return this.versionValue(value);
      default:
        return undefined;
    }
  }

  private numericValue(value?: string): number | undefined {
    return value && isNumeric(value) ? Number(value) : undefined;
  }

  private versionValue(value?: string) {
    const segments = value?.split('.');

    if (segments && segments.length) {
      const integerPart = isNumeric(segments[0]) ? segments[0] : '0';
      let decimalPart = '';

      segments.splice(1, segments.length - 1).forEach(segment => {
        const padSegment = segment.padEnd(3, '0');
        decimalPart = `${decimalPart}${isNumeric(padSegment) ? Number(padSegment) : 0}`;
      });

      return Number(`${integerPart}.${decimalPart}`);
    }

    return undefined;
  }
}
