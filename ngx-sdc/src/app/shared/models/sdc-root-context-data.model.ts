import { SseEventModel } from 'src/app/core/services';
import { SdcEventReference } from './sdc-event-reference.model';

export interface SdcOverlayContextData {
  events: SseEventModel<SdcEventReference>[];
}
