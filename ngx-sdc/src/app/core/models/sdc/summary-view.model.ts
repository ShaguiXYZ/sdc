export enum SummaryViewType {
  COMPONENT = 'COMPONENT',
  DEPARTMENT = 'DEPARTMENT',
  SQUAD = 'SQUAD'
}

export interface ISummaryViewDTO {
  id: number;
  name: string;
  type: SummaryViewType;
  coverage: number;
}

export interface ISummaryViewModel {
  id: number;
  name: string;
  type: SummaryViewType;
  coverage: number;
  trackId: string;
}

export namespace ISummaryViewModel {
  export const fromDTO = (dto: ISummaryViewDTO): ISummaryViewModel =>
    new SummaryViewModel(dto.id, dto.name, dto.type, dto.coverage, `${dto.type}_${dto.id}}`);
  export const toDTO = (model: ISummaryViewModel): ISummaryViewDTO => ({ ...model });
}

export class SummaryViewModel implements ISummaryViewModel {
  constructor(
    public id: number,
    public name: string,
    public type: SummaryViewType,
    public coverage: number,
    public trackId: string
  ) {}
}
