/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
export type OverlayItemStatus = 'open' | 'closed';

export namespace OverlayItemStatus {
  export const toggle = (state: OverlayItemStatus): OverlayItemStatus => (state === 'open' ? 'closed' : 'open');
}

export interface OverlayState {
  loaded?: boolean;
  status: OverlayItemStatus;
}

export interface SdcOverlayModel {
  eventBarState: OverlayState;
  globalSearchState: OverlayState;
  helpState: OverlayState;
  loginState: OverlayState;
}
