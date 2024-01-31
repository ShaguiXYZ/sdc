import { Pipe, PipeTransform } from '@angular/core';
import { isNumeric } from 'src/app/core/lib';
import { IAppConfigurationModel, ValueType } from 'src/app/core/models/sdc';
import { ContextDataService } from 'src/app/core/services';
import { ContextDataInfo } from '../constants';

@Pipe({
  name: 'sdcValueTypeToNumber'
})
export class SdcValueTypeToNumberPipe implements PipeTransform {
  private DEC_SUB = 3;

  constructor(private readonly contextDataService: ContextDataService) {
    const appConfig = contextDataService.get<IAppConfigurationModel>(ContextDataInfo.APP_CONFIG);

    this.DEC_SUB = appConfig?.analysis.precision ?? this.DEC_SUB;
  }

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
    const [version, ...segments] = value.replace(/[^0-9.]/g, '').split('.');
    const integerPart = isNumeric(version) ? version : '0';
    let decimalPart = '';

    segments?.forEach(segment => {
      const padSegment = isNumeric(segment) ? segment.padStart(this.DEC_SUB, '0') : ''.padStart(this.DEC_SUB, '0');
      decimalPart = `${decimalPart}${padSegment}`;
    });

    return Number(`${integerPart}.${decimalPart ?? 0}`);
  }
}
