/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
import { UniqueIds } from '@shagui/ng-shagui/core';
import { SseEventReferenceDTO, SseEventReferenceModel } from './sse-event-reference.model';

export type SseEventType = 'ERROR' | 'INFO';

export interface SseEventDTO {
  type: SseEventType;
  message: string;
  date: number;
  reference?: SseEventReferenceDTO;
}

export interface SseEventModel {
  id?: string;
  type: SseEventType;
  message: string;
  date: number;
  read?: boolean;
  reference?: SseEventReferenceModel;
}

export namespace SseEventModel {
  export const fromDTO = (dto: SseEventDTO): SseEventModel => ({
    id: UniqueIds._next_(),
    type: dto.type,
    message: dto.message,
    date: dto.date,
    read: false,
    reference: dto.reference && SseEventReferenceModel.fromDTO(dto.reference)
  });
}
