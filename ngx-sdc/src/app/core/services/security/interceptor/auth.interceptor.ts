/* eslint-disable @typescript-eslint/naming-convention */
import { HttpEvent, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { APP_NAME } from 'src/app/core/constants';
import { CONTEXT_WORKFLOW_ID, HEADER_AUTHORIZATION, HEADER_SESSION_ID, HEADER_WORKFLOW_ID, SecurityService } from 'src/app/core/services';

export const authInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> => {
  const securityService = inject(SecurityService);
  const source = APP_NAME.toUpperCase();

  const getToken = (): string => {
    const token = securityService.session?.token;
    return token ? `Bearer ${token}` : '';
  };

  const getSID = (): string => securityService.session?.sid || '';

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
  const httpRequest = req.clone({
    headers: req.headers
      .set('Source', source)
      .set(HEADER_AUTHORIZATION, getToken())
      .set(HEADER_SESSION_ID, getSID())
      .set(HEADER_WORKFLOW_ID, CONTEXT_WORKFLOW_ID)
      .set('Content-Type', 'application/json')
      .set('accept', 'application/json')
  });

  return next(httpRequest);
};
