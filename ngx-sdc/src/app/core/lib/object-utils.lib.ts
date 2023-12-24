/**
 * Make a deep copy of a object
 *
 * @param item Item to copy
 */
export const deepCopy = <T>(item: T): T => structuredClone(item);
// export const deepCopy = <T>(item: T): T => JSON.parse(JSON.stringify(item));

type GroupedTypes = string | number | symbol;

export type IndexdData<T> = Record<GroupedTypes, T[]>;

// @howto: use this function to indexed a collection of objects by a given property
export const indexBy = <T, K extends keyof T>(collection: T[], property: K): IndexdData<T> =>
  collection.reduce((acc: Record<GroupedTypes, T[]>, item: T) => {
    const key = item[property] as GroupedTypes;
    acc[key] = acc[key] ?? [];
    acc[key].push(item);

    return acc;
  }, {});

export const hasValue = (data: any): boolean => data !== null && data !== undefined;

export const isNumeric = (data: string): boolean => !isNaN(Number(data));

// @howto: use this function to filter a collection of objects by a given property
export const filterBy = <T>(collection: T[], properties: keyof T | (keyof T)[], filter: string): T[] => {
  const propertyList = Array.isArray(properties) ? properties : [properties];
  const searchWords: string[] = filter
    .toLowerCase()
    .split(' ')
    .filter(str => str.length > 1);
  const regex = searchWords.map(word => `(?=.*${word})`).join('');
  const searchExp = new RegExp(regex, 'gi');

  return collection.filter(data =>
    searchExp.test(
      propertyList
        .map(key => `${data[key]}`)
        .join(' ')
        .toLocaleLowerCase()
    )
  );
};

export const propertyIn = <T>(object: any, property: string): object is T => property in object;

// @howto: copy a text to the clipboard
export const copyToClipboard = (text: string): Promise<void> => navigator.clipboard.writeText(text);
