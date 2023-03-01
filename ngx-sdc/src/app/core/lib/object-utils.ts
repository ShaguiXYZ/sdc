import _equal from 'fast-deep-equal/es6';

/**
 * Make a deep copy of a object
 *
 * @param item Item to copy
 */
export const deepCopy = <T>(item: T): T => JSON.parse(JSON.stringify(item));
export const equal = <T>(data1: Partial<T>, data2: Partial<T>): boolean => _equal(data1, data2);