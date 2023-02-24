import { CONTEXT } from '@aposin/ng-aquila/message';
import { UniqueIds } from 'src/app/core/lib/keys';
import { DEFAULT_TIMEOUT_NOTIFICATIONS } from '../interfaces/app.constants';
import { ButtonModel } from './buttonModel';

export class NotificationModel {
    public id: string;

    constructor(
        public title: string,
        public description: string,
        public type: CONTEXT,
        public timeout: number = timeout !== null ? timeout : DEFAULT_TIMEOUT_NOTIFICATIONS,
        public closable: boolean = false,
        public button?: ButtonModel
    ) {
        this.id = `${UniqueIds.next()}`;
        this.closable = timeout && timeout > 0 ? this.closable : true;
    }
}

export enum NotificationType {
    SUCCESS = 'success',
    INFO = 'info',
    WARNING = 'warning',
    ERROR = 'error'
}
