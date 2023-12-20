import { ApplicationRef, Injectable, OnDestroy } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { SESSION_THEME_KEY, Theme } from '../models';

// @howto: service to handle theme changes
@Injectable()
export class ThemingService implements OnDestroy {
  public themeChange = new BehaviorSubject<Theme>('light');

  private darkThemeMq!: MediaQueryList;
  private _theme!: Theme;

  constructor(private ref: ApplicationRef) {
    const storedTheme = localStorage.getItem(SESSION_THEME_KEY);
    this.theme = storedTheme ? JSON.parse(storedTheme)?.active || 'light' : 'light';

    this.darkThemeMq = window.matchMedia?.('(prefers-color-scheme: dark)');

    if (this.darkThemeMq) {
      this.darkThemeMq.addEventListener('change', this.darkThemeMqListener.bind(this));
    }
  }

  ngOnDestroy(): void {
    this.darkThemeMq.removeEventListener('change', this.darkThemeMqListener.bind(this));
  }

  public get theme(): Theme {
    return this._theme;
  }
  public set theme(value: Theme) {
    if (this._theme === value) {
      return;
    }

    this._theme = value;
    document.body.setAttribute('data-theme', this.theme);
    localStorage.setItem(SESSION_THEME_KEY, JSON.stringify({ active: this.theme }));

    this.themeChange.next(this.theme);
  }

  private darkThemeMqListener = (e: MediaQueryListEvent): void => {
    this.theme = e.matches ? 'dark' : 'light';

    // trigger refresh of UI
    this.ref.tick();
  };
}
