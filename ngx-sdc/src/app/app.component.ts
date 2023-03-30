import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { UiContextDataService, UiSessionService } from './core/services';
import { ContextDataInfo } from './shared/constants/context-data';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [UiSessionService]
})
export class AppComponent implements OnInit, OnDestroy {
  public sessionLoaded = false;
  private language$!: Subscription;

  constructor(private contextData: UiContextDataService, private sessionService: UiSessionService) {}

  ngOnInit(): void {
    this.sessionService.sdcSession().then(session => {
      this.contextData.setContextData(ContextDataInfo.SDC_SESSION_DATA, session, { persistent: true });
      this.sessionLoaded = true;
    });
  }

  ngOnDestroy(): void {
    this.language$.unsubscribe();
  }
}
