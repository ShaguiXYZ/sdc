import { DataInfo } from '../../models';
import { CacheData } from '../../services/context-data';

export class ContextDataServiceMock {
  cache: DataInfo<CacheData> = {};

  set() {
    /* Mock method */
  }
  get() {
    /* Mock method */
  }

  delete() {
    /* Mock method */
  }
}
