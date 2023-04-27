import { GenericDataInfo } from 'src/app/core/interfaces/dataInfo';
import { MessageModal } from 'src/app/core/interfaces/modal';

export interface RequestOptions {
  clientOptions?: any;
  procesingMessage?: MessageModal;
  responseStatusMessage?: GenericDataInfo<MessageModal>;
  showLoading?: boolean;
  successMessage?: MessageModal;
}
