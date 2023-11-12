/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
import { IComponentModel } from 'src/app/core/models/sdc';

export interface IComplianceModel {
  id: number;
  name: string;
  coverage: number;
  parentName?: string;
  analysisDate?: number;
}

export namespace IComplianceModel {
  export const fromComponentModel = (data: IComponentModel): IComplianceModel => ({
    id: data.id,
    name: data.name,
    coverage: data.coverage || 0,
    analysisDate: data.analysisDate,
    parentName: data.squad.name
  });
}
