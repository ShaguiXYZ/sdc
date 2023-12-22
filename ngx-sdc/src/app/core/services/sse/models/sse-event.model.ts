export type SseEventType = 'ERROR' | 'INFO';

export interface SseEventModel {
  id?: string;
  type: SseEventType;
  workflowId: string;
  message: string;
  date: string;
}
