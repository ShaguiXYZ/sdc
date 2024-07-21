/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
export interface SseEventReferenceDTO {
  componentId?: number;
  componentName?: string;
  metricId?: number;
  metricName?: string;
}

export interface SseEventReferenceModel {
  componentId?: number;
  componentName?: string;
  metricId?: number;
  metricName?: string;
}

export namespace SseEventReferenceModel {
  export const fromDTO = (dto: SseEventReferenceDTO): SseEventReferenceModel => ({ ...dto });
}
