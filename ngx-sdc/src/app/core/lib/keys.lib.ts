/**
 * @howto: use this class to generate unique ids
 */
export class UniqueIds {
  private static idx: number = new Date().getTime();

  public static next = (): number => this.idx++;
  public static _next_ = (prefix?: string): string => `${prefix ?? ''}_${this.idx++}_`;
  public static uuid = (): string => crypto.randomUUID();
}

/**
 * @howto: use this function to generate a random string of 7 characters
 *
 * @returns a random string of 7 characters
 */
export const key = () => Math.random().toString(36).substring(7);
