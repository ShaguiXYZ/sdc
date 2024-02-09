import { MessageModal } from 'src/app/core/models';

export interface StatusMessage extends Partial<MessageModal> {
  fn?: (res: any) => void;
}
