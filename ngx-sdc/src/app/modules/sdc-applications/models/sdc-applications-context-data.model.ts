export interface ApplicationsFilter {
  squad?: number;
  name?: string;
}

export interface ApplicationsContextData {
  filter?: ApplicationsFilter;
  page: number;
}
