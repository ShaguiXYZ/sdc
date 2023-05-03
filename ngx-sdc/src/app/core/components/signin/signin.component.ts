import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { UiSecurityService } from 'src/app/core/services';
import { UiLoadingService } from '../loading';

@Component({ template: '' })
export class SigninComponent implements OnInit, OnDestroy {
  private params$!: Subscription;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private loadingService: UiLoadingService,
    private securityService: UiSecurityService
  ) {}

  ngOnInit(): void {
    this.loadingService.showLoading = true;

    this.params$ = this.route.params.subscribe(params => {
      this.securityService.session = { sid: params['sid'], token: params['token'] };

      this.securityService
        .authUser()
        .then(user => {
          this.securityService.user = user;
          this.router.navigate(['']);
        })
        .catch(error => {
          console.log(error);
          this.securityService.logout();
        });
    });
  }

  ngOnDestroy(): void {
    this.loadingService.showLoading = false;
    this.params$.unsubscribe();
  }
}
