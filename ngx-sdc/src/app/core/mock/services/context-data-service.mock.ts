import { GenericDataInfo } from '../../models';
import { CacheData } from '../../services/context-data';

export class ContextDataServiceMock {
  cache: GenericDataInfo<CacheData> = {};

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
