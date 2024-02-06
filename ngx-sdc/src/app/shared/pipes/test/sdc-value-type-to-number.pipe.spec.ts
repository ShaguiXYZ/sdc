import { ValueType } from 'src/app/core/models/sdc';
import { SdcValueTypeToNumberPipe } from '../sdc-value-type-to-number.pipe';
import { TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ContextDataService } from 'src/app/core/services';
import { ContextDataServiceMock } from 'src/app/core/mock/services';

describe('SdcValueTypeToNumberPipe', () => {
  let contextDataServiceMock: any;
  let pipe: SdcValueTypeToNumberPipe;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      providers: [{ provide: ContextDataService, useClass: ContextDataServiceMock }]
    });

    contextDataServiceMock = TestBed.inject(ContextDataService);

    pipe = new SdcValueTypeToNumberPipe(contextDataServiceMock);
  });

  it('should transform a string to a number', () => {
    expect(pipe.transform('1', ValueType.NUMERIC)).toBe(1);
  });

  it('should transform a string to a version', () => {
    expect(pipe.transform('0.0.1', ValueType.VERSION)).toBe(0.000001);
  });

  it('should transform a string to a version case one', () => {
    expect(pipe.transform('false', ValueType.BOOLEAN)).toBe(0);
  });

  it('should transform a string to a version case two', () => {
    expect(pipe.transform('true', ValueType.BOOLEAN)).toBe(1);
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
