export interface Deferred<T> {
  resolve: (value: T | PromiseLike<T>) => void;
  reject: (reason: any) => void;
  promise: Promise<T>;
}

const defer = <T = void>(): Deferred<T> => {
  let resolve!: (value: T | PromiseLike<T>) => void;
  let reject!: (reason: unknown) => void;

  const promise = new Promise<T>((res, rej) => {
    resolve = res;
    reject = rej;
  });

  return { resolve, reject, promise };
};

export default defer;
