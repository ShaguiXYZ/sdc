import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { ContextDataService } from '@shagui/ng-shagui/core';
import { ContextDataServiceMock } from 'src/app/core/mock/services';
import { ValueType } from 'src/app/core/models/sdc';
import { SdcValueTypeToNumberPipe } from '../sdc-value-type-to-number.pipe';

describe('SdcValueTypeToNumberPipe', () => {
  let contextDataServiceMock: any;
  let _pipe: SdcValueTypeToNumberPipe;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      providers: [{ provide: ContextDataService, useClass: ContextDataServiceMock }]
    });

    contextDataServiceMock = TestBed.inject(ContextDataService);

    _pipe = new SdcValueTypeToNumberPipe(contextDataServiceMock);
  });

  it('should transform a string to a number', () => {
    expect(_pipe.transform('1', ValueType.NUMERIC)).toBe(1);
  });

  it('should transform a string to a version', () => {
    expect(_pipe.transform('0.0.1', ValueType.VERSION)).toBe(0.000001);
  });

  it('should transform a string to a version case one', () => {
    expect(_pipe.transform('false', ValueType.BOOLEAN)).toBe(0);
  });

  it('should transform a string to a version case two', () => {
    expect(_pipe.transform('true', ValueType.BOOLEAN)).toBe(1);
  });

  describe('constructor', () => {
    it('should set DEC_SUB to appConfig.analysis.precision if available', () => {
      const pipe = new SdcValueTypeToNumberPipe(contextDataServiceMock);
      expect(pipe['DEC_SUB']).toBe(3);
    });

    it('should set DEC_SUB to default value if appConfig.analysis.precision is not available', () => {
      const pipe = new SdcValueTypeToNumberPipe(contextDataServiceMock);
      expect(pipe['DEC_SUB']).toBe(pipe['DEC_SUB']);
    });
  });
});
