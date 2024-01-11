export type OverlayItemState = 'open' | 'closed';

export namespace OverlayItemState {
  export const toggle = (state: OverlayItemState): OverlayItemState => {
    return state === 'open' ? 'closed' : 'open';
  };
}

export interface SdcOverlayModel {
  helpState: OverlayItemState;
  globalSearchState: OverlayItemState;
  eventBarState: OverlayItemState;
}
