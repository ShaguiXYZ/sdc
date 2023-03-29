/* eslint-disable max-classes-per-file */
/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable no-redeclare */
export interface IPagingDTO {
  pageIndex: number;
  pageSize: number;
  pages: number;
  elelments: number;
}

export interface IPageableDTO<T> {
  paging: IPagingDTO;
  page: T[];
}

export interface IPagingModel {
  pageIndex: number;
  pageSize: number;
  pages: number;
  elelments: number;
}

export class PagingModel implements IPagingModel {
  constructor(public pageIndex: number, public pageSize: number, public pages: number, public elelments: number) {}
}

export namespace IPagingModel {
  export const toModel = (dto: IPagingDTO): IPagingModel => new PagingModel(dto.pageIndex, dto.pageSize, dto.pages, dto.elelments);
  export const toDTO = (model: IPagingModel): IPagingDTO => ({ ...model });
}

export interface IPageableModel<T> {
  paging: IPagingModel;
  page: T[];
}

export class PageableModel<T> implements IPageableModel<T> {
  constructor(public paging: IPagingModel, public page: T[]) {}
}
