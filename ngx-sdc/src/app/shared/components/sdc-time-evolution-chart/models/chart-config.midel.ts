import { ValueType } from 'src/app/core/models/sdc';
import { VisualPiece } from './visual-piece.model';

export interface ChartConfig {
  name?: string;
  pieces?: VisualPiece[];
  values: { xAxis: string; data: string; type?: ValueType }[];
}
