import { UniqueIds } from 'src/app/core/lib';

export type SseEventType = 'ERROR' | 'INFO';

export interface SseEventDTO<T = any> {
  type: SseEventType;
  message: string;
  date: number;
  reference?: T;
}

export interface SseEventModel<T = any> {
  id?: string;
  type: SseEventType;
  message: string;
  date: number;
  read?: boolean;
  reference?: T;
}

export namespace SseEventModel {
  export function fromDTO(dto: SseEventDTO): SseEventModel {
    return {
      id: UniqueIds._next_(),
      type: dto.type,
      message: dto.message,
      date: dto.date,
      read: false,
      reference: { ...dto.reference }
    };
  }
}
