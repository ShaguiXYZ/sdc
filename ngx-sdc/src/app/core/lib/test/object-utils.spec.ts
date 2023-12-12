import { filterByProperties, indexBy } from '../object-utils.lib';

describe('filterByProperties', () => {
  const collection = [
    { name: 'John', age: 25, city: 'New York' },
    { name: 'Jane', age: 30, city: 'San Francisco' },
    { name: 'Bob', age: 35, city: 'New York' },
    { name: 'Alice', age: 40, city: 'Los Angeles' }
  ];

  it('should return an empty array when the collection is empty', () => {
    expect(filterByProperties([], 'name', 'John')).toEqual([]);
  });

  it('should return the entire collection when no filter is provided', () => {
    expect(filterByProperties(collection, 'name', '')).toEqual(collection);
  });

  it('should filter by a single property', () => {
    expect(filterByProperties(collection, 'name', 'John')).toEqual([{ name: 'John', age: 25, city: 'New York' }]);
  });

  it('should filter by multiple properties', () => {
    expect(filterByProperties(collection, ['name', 'city'], 'New York')).toEqual([
      { name: 'John', age: 25, city: 'New York' },
      { name: 'Bob', age: 35, city: 'New York' }
    ]);
  });

  it('should filter case-insensitively', () => {
    expect(filterByProperties(collection, 'city', 'new york')).toEqual([
      { name: 'John', age: 25, city: 'New York' },
      { name: 'Bob', age: 35, city: 'New York' }
    ]);
  });

  it('should filter by multiple search terms', () => {
    expect(filterByProperties(collection, ['name', 'city'], 'Jo New')).toEqual([{ name: 'John', age: 25, city: 'New York' }]);
  });

  it('should return an empty array when no matches are found', () => {
    expect(filterByProperties(collection, 'name', 'Foo')).toEqual([]);
  });
});

describe('groupBy', () => {
  it('should group an array of objects by a given property', () => {
    const collection = [
      { id: 1, name: 'John' },
      { id: 2, name: 'Jane' },
      { id: 3, name: 'John' }
    ];
    const result = indexBy(collection, 'name');
    expect(result).toEqual({
      John: [
        { id: 1, name: 'John' },
        { id: 3, name: 'John' }
      ],
      Jane: [{ id: 2, name: 'Jane' }]
    });
  });

  it('should return an empty object when given an empty array', () => {
    const data: any[] = [];
    const result = indexBy(data, 'name');
    expect(result).toEqual({});
  });
});
