import { RequestOptions } from './request-options.model';

export interface CacheRequestOptions extends RequestOptions {
  cache?:
    | string
    | {
        id: string;
        ttl?: number; // in milliseconds (time-to-live)
      };
}
