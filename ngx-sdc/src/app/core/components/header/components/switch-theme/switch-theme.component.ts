import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { NxSwitcherModule } from '@aposin/ng-aquila/switcher';
import { SESSION_THEME_KEY, Theme } from './models';

@Component({
  selector: 'nx-switch-theme',
  templateUrl: './switch-theme.component.html',
  styleUrls: ['./switch-theme.component.scss'],
  standalone: true,
  imports: [NxSwitcherModule]
})
export class SwitchThemeComponent implements OnInit, OnDestroy {
  private darkThemeMq!: MediaQueryList;
  private _theme: Theme = 'light';

  @Output()
  public themeChange: EventEmitter<Theme> = new EventEmitter<Theme>();

  ngOnInit(): void {
    this.setTheme();
  }

  ngOnDestroy(): void {
    this.darkThemeMq.removeEventListener('change', this.darkThemeMqListener.bind(this));
  }

  public get theme(): Theme {
    return this._theme;
  }
  @Input()
  public set theme(value: Theme) {
    if (this._theme === value) {
      return;
    }

    this._theme = value;
    document.body.setAttribute('data-theme', this.theme);
    localStorage.setItem(SESSION_THEME_KEY, JSON.stringify({ active: this.theme }));

    this.themeChange.emit(this.theme);
  }

  public switchTheme(): void {
    this.theme = this.theme === 'dark' ? 'light' : 'dark';
  }

  // @how-to: set theme based on system and user preference
  private setTheme(): void {
    const storedTheme = localStorage.getItem(SESSION_THEME_KEY);

    this.darkThemeMq = window.matchMedia('(prefers-color-scheme: dark)');
    this.darkThemeMq.addEventListener('change', this.darkThemeMqListener.bind(this));

    if (storedTheme) {
      const parsedTheme = JSON.parse(storedTheme);
      this.theme = parsedTheme.active;
    } else {
      this.theme = this.darkThemeMq.matches ? 'dark' : 'light';
    }
  }

  private darkThemeMqListener = (e: MediaQueryListEvent): void => {
    this.theme = e.matches ? 'dark' : 'light';
  };
}
