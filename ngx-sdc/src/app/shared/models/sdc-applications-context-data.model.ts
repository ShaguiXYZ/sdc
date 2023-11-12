export interface ApplicationsFilter {
  coverage?: string;
  squad?: number;
  name?: string;
}

export interface ApplicationsContextData {
  filter?: ApplicationsFilter;
  page: number;
}
