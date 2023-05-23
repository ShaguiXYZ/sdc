import { IDepartmentModel } from '../../../core/models/sdc';

export interface SdcDepartmentsContextData {
  departmentFilter?: string;
  squadFilter?: string;
  department?: IDepartmentModel;
}
