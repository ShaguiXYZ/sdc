import { Component, OnInit } from '@angular/core';
import { IUserModel, SecurityService } from 'src/app/core/services';
import { SdcOverlayService } from '../sdc-overlay/services';
import { CommonModule } from '@angular/common';
import { NxTooltipModule } from '@aposin/ng-aquila/tooltip';

@Component({
  selector: 'sdc-log-in-out',
  template: `
    <div class="sdc-log-in-out" [ngClass]="{ 'sdc-user': user, 'sdc-not-user': !user }">
      <em class="fa-solid fa-user" [ngClass]="{ 'fa-user': user, 'fa-user-slash': !user }" (click)="toggleAuthentication()">
        {{ user?.userName }}</em
      >
    </div>
  `,
  styles: [
    `
      .sdc-log-in-out {
        border: 1px solid var(--sdc-primary-color);
        border-radius: 50px;
        cursor: pointer;
        font-size: 1rem;
        opacity: 0.6;
        padding: 5px 15px;
        transition:
          opacity 0.5s ease-in-out,
          background-color 1s ease-in-out;

        &.sdc-user {
          background-color: var(--success);
          color: var(--avatar-color);
        }

        &.sdc-not-user {
          background-color: var(--warning);
        }

        &:hover {
          opacity: 1;
        }
      }
    `
  ],
  imports: [CommonModule, NxTooltipModule],
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
