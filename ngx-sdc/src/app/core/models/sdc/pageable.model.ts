export interface IPaging {
  pageIndex: number;
  pageSize: number;
  pages: number;
  elelments: number;
}

export interface IPageable<T> {
  paging: IPaging;
  page: T[];
}
