import { DefaultCountdownSeed } from '../seed';

export interface CountdownSeed {
  // period: Countdown refresh time
  period?: () => number;
  // next: Next countdown value
  next: () => any;
  // hasNext: True if it exists if it exists next value false if it doesn't
  hasNext: () => boolean;
  // reset: Reset the countdown and start over
  reset: () => void;
  // warn: Values of the countdown that generates a warning
  warn?: () => boolean;
}

export const DEFAULT_SEED = (config?: any): CountdownSeed => new DefaultCountdownSeed(config);
