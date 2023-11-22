import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NxSwitcherModule } from '@aposin/ng-aquila/switcher';
import { SESSION_THEME_KEY, Theme } from './models';

@Component({
  selector: 'nx-switch-theme',
  templateUrl: './switch-theme.component.html',
  styleUrls: ['./switch-theme.component.scss'],
  standalone: true,
  imports: [NxSwitcherModule]
})
export class SwitchThemeComponent implements OnInit {
  private _theme: Theme = 'light';

  @Output()
  public themeChange: EventEmitter<Theme> = new EventEmitter<Theme>();

  ngOnInit(): void {
    this.setTheme();
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

  private setTheme(): void {
    const storedTheme = localStorage.getItem(SESSION_THEME_KEY);

    if (storedTheme) {
      const parsedTheme = JSON.parse(storedTheme);
      this.theme = parsedTheme.active;
    }
  }
}
