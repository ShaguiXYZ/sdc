import { DataInfo, MessageModal } from 'src/app/core/models';
import { StatusMessage } from './status-message.model';

export interface RequestOptions {
  clientOptions?: any;
  procesingMessage?: MessageModal;
  responseStatusMessage?: DataInfo<StatusMessage>;
  showLoading?: boolean;
  successMessage?: MessageModal;
  onSuccess?: (res: any) => void;
  onError?: (err: any) => void;
}
