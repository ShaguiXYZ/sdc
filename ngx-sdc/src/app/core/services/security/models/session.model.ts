/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
export interface ISessionDTO {
  sid: string;
  token: string;
}

export interface ISessionModel {
  sid: string;
  token: string;
}

export class SessionModel implements ISessionModel {
  constructor(
    public sid: string,
    public token: string
  ) {}
}

export namespace ISessionModel {
  export const fromDTO = (dto: ISessionDTO): ISessionModel => new SessionModel(dto.sid, dto.token);
  export const toDTO = (model: ISessionModel): ISessionDTO => ({
    ...model
  });
}
