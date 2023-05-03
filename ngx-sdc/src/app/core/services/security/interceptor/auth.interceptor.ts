/* eslint-disable @typescript-eslint/naming-convention */
import { HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { APP_NAME } from 'src/app/core/constants';
import { UiSecurityService } from 'src/app/core/services';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private securityService: UiSecurityService) {}

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
        .set(`Authorization`, this.getToken())
        .set('SID', this.getSID())
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
