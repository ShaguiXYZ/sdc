export interface IPaging {
  pageIndex: number;
  pageSize: number;
  pages: number;
  elements: number;
}

export interface IPageable<T> {
  paging: IPaging;
  page: T[];
}
