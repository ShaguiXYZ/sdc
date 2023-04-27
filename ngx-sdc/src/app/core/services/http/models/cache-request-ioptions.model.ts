import { RequestOptions } from './request-options.model';

export interface CacheRequestOptions extends RequestOptions {
  cache?: string | { id: string; cachedDuring?: number };
}
