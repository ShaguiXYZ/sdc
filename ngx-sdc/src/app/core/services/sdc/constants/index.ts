import { UniqueIds } from 'src/app/core/lib';

export const _COMPONENT_CACHE_ID_ = `_${UniqueIds.next()}_`;
export const _DEPARTMENT_CACHE_ID_ = `_${UniqueIds.next()}_`;
export const _SQUADS_CACHE_ID_ = `_${UniqueIds.next()}_`;

export const COMPONENT_EXPIRATON_TIME = 1 * 60 * 1000;
export const DEPARTMENTS_EXPIRATON_TIME = 30 * 60 * 1000;
export const SQUADS_EXPIRATON_TIME = 25 * 60 * 1000;
