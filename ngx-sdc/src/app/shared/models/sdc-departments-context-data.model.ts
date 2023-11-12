import { IDepartmentModel } from 'src/app/core/models/sdc';

export interface SdcDepartmentsContextData {
  departmentFilter?: string;
  squadFilter?: string;
  department?: IDepartmentModel;
}
