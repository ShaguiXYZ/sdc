export class UniqueIds {
  private static idx: number = new Date().getTime();

  public static next = (): number => this.idx++;
  public static uuid = (): string => crypto.randomUUID();
}

export const key = () => Math.random().toString(36).substring(7);
