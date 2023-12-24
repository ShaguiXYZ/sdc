import { SseEventModel } from 'src/app/core/services';
import { SdcEventReference } from './sdc-event-reference.model';

export interface SdcRootContextData {
  events: SseEventModel<SdcEventReference>[];
  eventsState: 'open' | 'closed';
}
