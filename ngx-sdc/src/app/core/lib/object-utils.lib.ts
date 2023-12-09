/**
 * Make a deep copy of a object
 *
 * @param item Item to copy
 */
export const deepCopy = <T>(item: T): T => structuredClone(item);
// export const deepCopy = <T>(item: T): T => JSON.parse(JSON.stringify(item));

//group by property
export const groupBy = <T>(collection: T[], property: keyof T): Record<string, T[]> =>
  collection.reduce((groups: Record<string, T[]>, item: T) => {
    const group = item[property];
    groups[group as string] = groups[group as string] || [];
    groups[group as string].push(item);
    return groups;
  }, {});

export const hasValue = (data: any): boolean => data !== null && data !== undefined;

export const isNumeric = (data: string): boolean => !isNaN(Number(data));

export const filterByProperties = <T>(collection: T[], properties: keyof T | (keyof T)[], filter: string): T[] => {
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
