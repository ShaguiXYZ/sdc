export interface IPagingDTO {
  pageIndex: number;
  pageSize: number;
  total: number;
}

export interface IPageableDTO<T> {
  paging: IPagingDTO;
  page: T[];
}

export interface IPagingModel {
  pageIndex: number;
  pageSize: number;
  total: number;
}

export class PagingModel implements IPagingModel {
  constructor(public pageIndex: number, public pageSize: number, public total: number) {}
}

export namespace IPagingModel {
  export const toModel = (dto: IPagingDTO): IPagingModel => new PagingModel(dto.pageIndex, dto.pageSize, dto.total);
  export const toDTO = (model: IPagingModel): IPagingDTO => ({ ...model });
}

export interface IPageableModel<T> {
  paging: IPagingModel;
  page: T[];
}

export class PageableModel<T> implements IPageableModel<T> {
  constructor(public paging: IPagingModel, public page: T[]) {}
}
