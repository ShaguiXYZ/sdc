import { APP_NAME } from 'src/app/core/constants';
import { UniqueIds } from 'src/app/core/lib';

export const HEADER_AUTHORIZATION = 'Authorization';
export const HEADER_SESSION_ID = 'SID';
export const HEADER_WORKFLOW_ID = 'WorkflowId';

export const CONTEXT_SECURITY_KEY = `${APP_NAME.toUpperCase()}-SECURITY-CONTEXT-DATA`;
export const CONTEXT_WORKFLOW_ID = UniqueIds._next_();
