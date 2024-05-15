import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';
import { NxPopoverModule } from '@aposin/ng-aquila/popover';
import { TranslateModule } from '@ngx-translate/core';
import { IUserModel, SecurityService } from 'src/app/core/services';
import { SdcOverlayService } from '../sdc-overlay/services';

@Component({
  selector: 'sdc-log-in-out',
  styleUrls: ['./sdc-log-in-out.component.scss'],
  template: `
    <div
      class="sdc-log-in-out sdc-user"
      [ngClass]="{ 'sdc-user': user, 'sdc-not-user': !user }"
      [nxPopoverTriggerFor]="popoverUserData"
      nxPopoverDirection="top"
      nxPopoverTrigger="hover"
      (click)="toggleAuthentication()"
    >
      <em class="fa-solid fa-user" [ngClass]="{ 'fa-user': user, 'fa-user-slash': !user }"></em>
    </div>

    <nx-popover #popoverUserData>
      @if (user) {
        <article class="sdc-user-container">
          <header>
            <em class="fa-solid fa-user"> {{ user.userName }}</em>
          </header>
          <section class="sdc-user-data">
            <div class="sdc-user-field">
              <label nxCopytext="large">{{ 'Label.Name' | translate }}</label>
              <p nxCopytext="medium">{{ user.name }} {{ user.surname }} {{ user.secondSurname }}</p>
            </div>
            <div class="sdc-user-field">
              <label nxCopytext="large">{{ 'Label.EMail' | translate }}</label>
              <p nxCopytext="medium">{{ user.email }}</p>
            </div>
          </section>
        </article>
        <div class="sdc-user-info">{{ 'Label.ToLogout' | translate }}</div>
      } @else {
        <div class="sdc-user-info">{{ 'Label.ToLogin' | translate }}</div>
      }
    </nx-popover>
  `,
  imports: [CommonModule, NxCopytextModule, NxPopoverModule, TranslateModule],
  standalone: true
})
export class SdcLogInOutComponent implements OnInit {
  public user?: IUserModel;

  constructor(
    private readonly overlayService: SdcOverlayService,
    private readonly securityService: SecurityService
  ) {}

  ngOnInit(): void {
    this.securityService.onSignInOut().subscribe(security => (this.user = security?.user));

    this.user = this.securityService.user;
  }

  public toggleAuthentication(): void {
    if (this.user) {
      this.securityService.logout();
    } else {
      this.overlayService.toggleLogin();
    }
  }
}
