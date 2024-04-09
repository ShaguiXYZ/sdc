import { UniqueIds } from '@shagui/ng-shagui/core';
import { APP_NAME } from 'src/app/core/constants';

export const HEADER_AUTHORIZATION = 'Authorization';
export const HEADER_SESSION_ID = 'SID';
export const HEADER_WORKFLOW_ID = 'WorkflowId';
export const CONTEXT_SECURITY_KEY = `${APP_NAME.toUpperCase()}__CTX_SECURITY`;
export const CONTEXT_WORKFLOW_ID = UniqueIds._next_();
