import { ValueType } from "src/app/core/models/sdc";
import { SdcValueTypeToNumberPipe } from "./sdc-value-type-to-number.pipe";

describe('SdcValueTypeToNumberPipe', () => {
    let pipe: SdcValueTypeToNumberPipe;
  
    beforeEach(() => {
        pipe = new SdcValueTypeToNumberPipe();
    });
  
    it('should transform a string to a number', () => {
      expect(pipe.transform('1', ValueType.NUMERIC)).toBe(1);
    });

    it('should transform a string to a version', () => {
        expect(pipe.transform('0.0.1', ValueType.VERSION)).toBe(0.01);
      });
  });