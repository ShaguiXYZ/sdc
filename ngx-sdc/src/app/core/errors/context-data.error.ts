export class ContextDataError extends Error {
  constructor(message: string) {
    super(message);
    this.name = 'ContextDataError';
  }
}
