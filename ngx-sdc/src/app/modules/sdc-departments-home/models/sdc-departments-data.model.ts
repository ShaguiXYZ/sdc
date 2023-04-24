import { IDepartmentModel, ISquadModel } from 'src/app/core/models/sdc';

export interface SdcDepartmentsDataModel {
  department?: IDepartmentModel;
  departments: IDepartmentModel[];
  departmentFilter?: string;
  squadFilter?: string;
  squads: ISquadModel[];
  squadsInView: ISquadModel[];
}
