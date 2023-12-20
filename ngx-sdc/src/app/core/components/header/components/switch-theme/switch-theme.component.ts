import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NxSwitcherModule } from '@aposin/ng-aquila/switcher';
import { Theme } from './models';
import { ThemingService } from './services';

@Component({
  selector: 'nx-switch-theme',
  templateUrl: './switch-theme.component.html',
  styleUrls: ['./switch-theme.component.scss'],
  providers: [ThemingService],
  standalone: true,
  imports: [NxSwitcherModule]
})
export class SwitchThemeComponent implements OnInit {
  @Output()
  public themeChange: EventEmitter<Theme> = new EventEmitter<Theme>();

  constructor(private temingService: ThemingService) {}

  ngOnInit(): void {
    this.temingService.themeChange.subscribe((theme: Theme) => {
      this.theme = theme;
      this.themeChange.emit(this.theme);
    });
  }

  public get theme(): Theme {
    return this.temingService.theme;
  }
  @Input()
  public set theme(value: Theme) {
    this.temingService.theme = value;
  }

  public switchTheme(): void {
    this.theme = this.theme === 'dark' ? 'light' : 'dark';
  }
}
