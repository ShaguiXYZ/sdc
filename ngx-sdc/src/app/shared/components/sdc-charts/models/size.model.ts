import { DataInfo } from 'src/app/core/models';

export type ChartSize = number | string | { height?: number | string; width?: number | string };

export class SdcChartSize {
  constructor(private size?: ChartSize) {}

  public get styleSize(): DataInfo<number | string> {
    const styleSize: DataInfo<number | string> = {};

    if (typeof this.size === 'number') {
      styleSize['height.px'] = this.size;
      styleSize['width.px'] = this.size;
    } else if (typeof this.size === 'string') {
      styleSize['height'] = this.size;
      styleSize['width'] = this.size;
    } else if (typeof this.size === 'object') {
      const { height, width } = this.size;

      if (typeof height === 'string') {
        styleSize['height'] = height;
      } else if (typeof height === 'number') {
        styleSize['height.px'] = height;
      }

      if (typeof width === 'string') {
        styleSize['width'] = width;
      } else if (typeof width === 'number') {
        styleSize['width.px'] = width;
      }
    }

    return styleSize;
  }
}