/* eslint-disable @typescript-eslint/naming-convention */
import { HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class CacheInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler) {
        const httpRequest = req.clone({
            headers: req.headers
                .set('Cache-Control', 'no-cache')
                .set('Pragma', 'no-cache')
                .set('Expires', '0')
                .set('If-Modified-Since', '0')
        });

        return next.handle(httpRequest);
    }
}
