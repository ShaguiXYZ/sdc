export class Stack<T> {
  private stack: T[] = [];
  private readonly maxSize: number = 50;

  constructor(maxSize?: number) {
    if (maxSize) {
      this.maxSize = maxSize;
    }
  }

  toArray(): T[] {
    return this.stack;
  }

  push(item: T): void {
    if (this.stack.length === this.maxSize) {
      this.stack.shift();
    }

    this.stack.push(item);
  }

  pop(): T | undefined {
    return this.stack.pop();
  }
}
