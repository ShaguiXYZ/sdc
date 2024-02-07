import { MessageModal } from 'src/app/core/models';

export interface StatusMessage extends MessageModal {
  fn?: (res: any) => void;
}
