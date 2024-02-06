export type OverlayItemStatus = 'open' | 'closed';

export namespace OverlayItemStatus {
  export const toggle = (state: OverlayItemStatus): OverlayItemStatus => {
    return state === 'open' ? 'closed' : 'open';
  };
}

export interface SdcOverlayModel {
  eventBarState: OverlayItemStatus;
  globalSearchState: OverlayItemStatus;
  helpState: OverlayItemStatus;
  loginState: OverlayItemStatus;
}
