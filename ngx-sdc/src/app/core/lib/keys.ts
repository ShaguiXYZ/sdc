
export class UniqueIds {
  private static idx: number = new Date().getTime();

  public static next(): number {
    return this.idx++;
  }
}
