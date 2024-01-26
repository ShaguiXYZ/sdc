export type OverlayItemStatus = 'open' | 'closed';

export namespace OverlayItemStatus {
  export const toggle = (state: OverlayItemStatus): OverlayItemStatus => {
    return state === 'open' ? 'closed' : 'open';
  };
}

export interface SdcOverlayModel {
  helpState: OverlayItemStatus;
  globalSearchState: OverlayItemStatus;
  eventBarState: OverlayItemStatus;
}
