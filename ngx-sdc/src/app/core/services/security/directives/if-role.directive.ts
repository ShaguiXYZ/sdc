import { Directive, Input, OnDestroy, TemplateRef, ViewContainerRef } from '@angular/core';
import { Subscription } from 'rxjs';
import { SecurityService } from '../security.service';

@Directive({
  selector: '[ifRole]',
  standalone: true
})
export class IfRoleDirective implements OnDestroy {
  private roles: string[] = [];
  private security$: Subscription;

  constructor(
    private readonly templateRef: TemplateRef<any>,
    private readonly viewContainer: ViewContainerRef,
    private readonly securityService: SecurityService
  ) {
    this.security$ = securityService.onSignInOut().subscribe(() => this.checkRoles());
  }

  @Input()
  set ifRole(roles: string[] | undefined) {
    this.roles = roles ?? [];
    this.checkRoles();
  }

  ngOnDestroy(): void {
    this.security$.unsubscribe();
  }

  private checkRoles(): void {
    const userRoles = this.securityService.user?.authorities ?? [];
    const shouldShow = !this.roles?.length || this.roles.some(role => userRoles.includes(role));

    shouldShow ? this.viewContainer.createEmbeddedView(this.templateRef) : this.viewContainer.clear();
  }
}
