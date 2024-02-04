import { DataInfo } from 'src/app/core/models';

export type ChartSize =
  | number
  | string
  | {
      height?: number | string;
      width?: number | string;
      'max-height'?: number | string;
      'max-width'?: number | string;
      'min-height'?: number | string;
      'min-width'?: number | string;
    };

export class SdcChartSize {
  constructor(private size?: ChartSize) {}

  public get styleSize(): DataInfo<number | string> {
    const styleSize: DataInfo<number | string> = {};

    if (typeof this.size === 'number') {
      this.setNumericStyleSize(styleSize, this.size);
    } else if (typeof this.size === 'string') {
      this.setStringStyleSize(styleSize, this.size);
    } else if (typeof this.size === 'object') {
      this.setObjectStyleSize(styleSize, this.size);
    }

    return styleSize;
  }

  private setNumericStyleSize(styleSize: DataInfo<number | string>, size: number): void {
    styleSize['height.px'] = size;
    styleSize['width.px'] = size;
  }

  private setStringStyleSize(styleSize: DataInfo<number | string>, size: string): void {
    styleSize['height'] = size;
    styleSize['width'] = size;
  }

  private setObjectStyleSize(
    styleSize: DataInfo<number | string>,
    size: {
      height?: number | string;
      width?: number | string;
      'max-height'?: number | string;
      'max-width'?: number | string;
      'min-height'?: number | string;
      'min-width'?: number | string;
    }
  ): void {
    const { height, width, 'max-height': maxHeight, 'max-width': maxWidth, 'min-height': minHeight, 'min-width': minWidth } = size;

    this.setDimensionStyleSize(styleSize, 'height', height);
    this.setDimensionStyleSize(styleSize, 'width', width);
    this.setDimensionStyleSize(styleSize, 'max-height', maxHeight);
    this.setDimensionStyleSize(styleSize, 'max-width', maxWidth);
    this.setDimensionStyleSize(styleSize, 'min-height', minHeight);
    this.setDimensionStyleSize(styleSize, 'min-width', minWidth);
  }

  private setDimensionStyleSize(styleSize: DataInfo<number | string>, property: string, value?: number | string): void {
    if (typeof value === 'string') {
      styleSize[property] = value;
    } else if (typeof value === 'number') {
      styleSize[`${property}.px`] = value;
    }
  }
}
