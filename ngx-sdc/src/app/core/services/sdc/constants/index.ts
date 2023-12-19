import { UniqueIds } from 'src/app/core/lib';

export const _COMPONENT_CACHE_ID_ = `_${UniqueIds.next()}_`;
export const _CONFIGURATION_CACHE_ID_ = `_${UniqueIds.next()}_`;
export const _DATA_LIST_CACHE_ID_ = `_${UniqueIds.next()}_`;
export const _DEPARTMENT_CACHE_ID_ = `_${UniqueIds.next()}_`;
export const _METRICS_CACHE_ID_ = `_${UniqueIds.next()}_`;
export const _SQUADS_CACHE_ID_ = `_${UniqueIds.next()}_`;
export const _TAGS_CACHE_ID_ = `_${UniqueIds.next()}_`;

export const XS_EXPIRATON_TIME = 60 * 1000; // 1 Minute
export const S_EXPIRATON_TIME = 15 * XS_EXPIRATON_TIME;
export const M_EXPIRATON_TIME = 30 * XS_EXPIRATON_TIME;
export const L_EXPIRATON_TIME = 60 * XS_EXPIRATON_TIME; // 1 Hour
export const XL_EXPIRATON_TIME = 5 * L_EXPIRATON_TIME;
export const XXL_EXPIRATON_TIME = 24 * L_EXPIRATON_TIME;
