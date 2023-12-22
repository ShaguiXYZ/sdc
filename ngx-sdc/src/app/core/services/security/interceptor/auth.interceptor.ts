/* eslint-disable @typescript-eslint/naming-convention */
import { HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { APP_NAME } from 'src/app/core/constants';
import { CONTEXT_WORKFLOW_ID, HEADER_AUTHORIZATION, HEADER_SESSION_ID, HEADER_WORKFLOW_ID, SecurityService } from 'src/app/core/services';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private securityService: SecurityService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    /**
     * Overwriting all headers
     */
    // const httpRequest = req.clone({
    //     headers: new HttpHeaders({
    //         Authorization: this.getToken(),
    //         'Content-Type': 'application/json',
    //         accept: 'application/json'
    //     })
    // });

    /**
     * Adding more headers without overwriting
     */
    const source = APP_NAME.toUpperCase();

    const httpRequest = req.clone({
      headers: req.headers
        .set('Source', source)
        .set(HEADER_AUTHORIZATION, this.getToken())
        .set(HEADER_SESSION_ID, this.getSID())
        .set(HEADER_WORKFLOW_ID, CONTEXT_WORKFLOW_ID)
        .set('Content-Type', 'application/json')
        .set('accept', 'application/json')
    });

    return next.handle(httpRequest);
  }

  private getToken(): string {
    const token = this.securityService.session?.token;
    return token ? `Bearer ${token}` : '';
  }

  private getSID(): string {
    return this.securityService.session?.sid || '';
  }
}
