export interface ApplicationsFilter {
  coverage?: string;
  squad?: number;
  name?: string;
  tags?: string[];
}

export interface ApplicationsContextData {
  filter?: ApplicationsFilter;
  page: number;
}
