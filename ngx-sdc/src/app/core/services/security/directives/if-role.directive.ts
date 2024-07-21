import { Directive, ElementRef, Input, OnDestroy, TemplateRef, ViewContainerRef } from '@angular/core';
import { Subscription } from 'rxjs';
import { SecurityService } from '../security.service';

@Directive({
  selector: '[nxIfRole]',
  standalone: true
})
export class IfRoleDirective implements OnDestroy {
  private roles: string[] = [];
  private security$: Subscription;

  constructor(
    private readonly templateRef: TemplateRef<ElementRef>,
    private readonly viewContainer: ViewContainerRef,
    private readonly securityService: SecurityService
  ) {
    this.security$ = securityService.onSignInOut().subscribe(() => this.checkRoles());
  }

  @Input()
  set nxIfRole(roles: string[] | undefined) {
    this.roles = roles ?? [];
    this.checkRoles();
  }

  ngOnDestroy(): void {
    this.security$.unsubscribe();
  }

  private checkRoles(): void {
    const userRoles = this.securityService.user?.authorities ?? [];
    const shouldShow = !this.roles?.length || this.roles.some(role => userRoles.includes(role));

    if (shouldShow) this.viewContainer.createEmbeddedView(this.templateRef);
    else this.viewContainer.clear();
  }
}
