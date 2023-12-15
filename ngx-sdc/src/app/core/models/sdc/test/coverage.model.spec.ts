import { ICoverageModel } from '../coverage.model';

describe('ICoverageModel', () => {
  it('should sort by coverage and then by name', () => {
    const data = [
      { name: 'b', coverage: 50 },
      { name: 'a', coverage: 100 },
      { name: 'c', coverage: 75 }
    ];
    const sortedData = data.sort(ICoverageModel.sort);
    expect(sortedData).toEqual([
      { name: 'b', coverage: 50 },
      { name: 'c', coverage: 75 },
      { name: 'a', coverage: 100 }
    ]);
  });

  it('should place components without coverage at the end', () => {
    const data = [
      { name: 'b', coverage: 50 },
      { name: 'a', coverage: undefined },
      { name: 'c', coverage: 75 }
    ];
    const sortedData = data.sort(ICoverageModel.sort);
    expect(sortedData).toEqual([
      { name: 'b', coverage: 50 },
      { name: 'c', coverage: 75 },
      { name: 'a', coverage: undefined }
    ]);
  });
});
