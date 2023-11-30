import { DataInfo, MessageModal } from 'src/app/core/models';

export interface RequestOptions {
  clientOptions?: any;
  procesingMessage?: MessageModal;
  responseStatusMessage?: DataInfo<MessageModal>;
  showLoading?: boolean;
  successMessage?: MessageModal;
}
