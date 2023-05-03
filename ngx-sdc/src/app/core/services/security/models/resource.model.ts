/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
export interface IResourceDTO {
    name: string;
    uri: string;
}

export interface IResourceModel {
    name: string;
    uri: string;
}

export class ResourceModel implements IResourceModel {
    constructor(public name: string, public uri: string) {}
}

export namespace IResourceModel {
    export const toModel = (dto: IResourceDTO): IResourceModel => new ResourceModel(dto.name, dto.uri);

    export const toDTO = (model: ResourceModel): IResourceDTO => ({
        name: model.name,
        uri: model.uri
    });
}
