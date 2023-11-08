import { GenericDataInfo, MessageModal } from 'src/app/core/models';

export interface RequestOptions {
  clientOptions?: any;
  procesingMessage?: MessageModal;
  responseStatusMessage?: GenericDataInfo<MessageModal>;
  showLoading?: boolean;
  successMessage?: MessageModal;
}
