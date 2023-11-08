import { HttpClient, HttpErrorResponse, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Observable, of, take } from 'rxjs';
import { finalize, tap } from 'rxjs/operators';
import { UiLoadingService } from '../../components/loading/services';
import { DEFAULT_TIMEOUT_NOTIFICATIONS } from '../../components/notification';
import { UiNotificationService } from '../../components/notification/services';
import { GenericDataInfo, MessageModal } from '../../models';
import { UiCacheService } from '../context-data';
import { HttpStatus } from './constants';
import { CacheRequestOptions, RequestOptions } from './models';

@Injectable({
  providedIn: 'root'
})
export class UiHttpService {
  constructor(
    private http: HttpClient,
    private translateService: TranslateService,
    private cache: UiCacheService,
    private loadingService: UiLoadingService,
    private notificationService: UiNotificationService
  ) {}

  public get<T>(url: string, requestOptions?: CacheRequestOptions): Observable<T | HttpEvent<T>> {
    let cacheId: string | undefined;
    let expiration: number | undefined;

    if (typeof requestOptions?.cache === 'string') {
      cacheId = requestOptions?.cache;
    } else if (requestOptions?.cache) {
      cacheId = requestOptions?.cache.id;
      expiration = this.cache.expirationDate(requestOptions?.cache.cachedDuring);
    }

    let cachedData: T | undefined;

    if (cacheId) {
      cachedData = this.cache.get(cacheId) as T;
    }

    if (cachedData) {
      return of(cachedData).pipe(take(1));
    } else {
      if (requestOptions?.showLoading) {
        this.loadingService.showLoading = true;
      }

      return this.http.get<T>(url, requestOptions?.clientOptions).pipe(
        tap(data => {
          if (cacheId) {
            this.cache.set(cacheId, data, expiration);
          }
        }),
        tap(this.tabControl(requestOptions)),
        finalize(() => {
          if (requestOptions?.showLoading) {
            this.loadingService.showLoading = false;
          }
        })
      );
    }
  }

  public _post<T>(url: string, body?: T, requestOptions?: RequestOptions): Observable<T | HttpEvent<T>> {
    return this.post<T, T>(url, body, requestOptions);
  }

  public post<OUT, IN>(url: string, body?: IN, requestOptions?: RequestOptions): Observable<OUT | HttpEvent<OUT>> {
    const notificationId = this.notificationService.info(
      this.translateService.instant(requestOptions?.procesingMessage?.title ?? 'Notifications.Procesing'),
      this.translateService.instant(requestOptions?.procesingMessage?.text ?? 'Notifications.ProcesingDetail'),
      0,
      false
    );

    this.loadingService.showLoading = true;
    return this.http.post<OUT>(url, body, requestOptions?.clientOptions).pipe(
      tap(this.tabControl(requestOptions)),
      finalize(() => {
        this.notificationService.closeNotification(notificationId);
        this.loadingService.showLoading = false;
      })
    );
  }

  public _put<T>(url: string, body?: T, requestOptions?: RequestOptions): Observable<T | HttpEvent<T>> {
    return this.put<T, T>(url, body, requestOptions);
  }

  public put<OUT, IN>(url: string, body?: IN, requestOptions?: RequestOptions): Observable<OUT | HttpEvent<OUT>> {
    const notificationId = this.notificationService.info(
      this.translateService.instant(requestOptions?.procesingMessage?.title ?? 'Notifications.Procesing'),
      this.translateService.instant(requestOptions?.procesingMessage?.text ?? 'Notifications.ProcesingDetail'),
      0,
      false
    );

    this.loadingService.showLoading = true;
    return this.http.put<OUT>(url, body, requestOptions?.clientOptions).pipe(
      tap(this.tabControl(requestOptions)),
      finalize(() => {
        this.notificationService.closeNotification(notificationId);
        this.loadingService.showLoading = false;
      })
    );
  }

  public _patch<T>(url: string, body: Partial<T>, requestOptions?: RequestOptions): Observable<T | HttpEvent<T>> {
    return this.patch<T, T>(url, body, requestOptions);
  }

  public patch<OUT, IN>(url: string, body: Partial<IN>, requestOptions?: RequestOptions): Observable<OUT | HttpEvent<OUT>> {
    if (requestOptions?.showLoading) {
      this.loadingService.showLoading = true;
    }

    return this.http.patch<OUT>(url, body, requestOptions?.clientOptions).pipe(
      tap(this.tabControl(requestOptions)),
      finalize(() => {
        if (requestOptions?.showLoading) {
          this.loadingService.showLoading = false;
        }
      })
    );
  }

  public delete<T>(url: string, requestOptions?: RequestOptions): Observable<any> {
    const notificationId = this.notificationService.info(
      this.translateService.instant(requestOptions?.procesingMessage?.title ?? 'Notifications.Procesing'),
      this.translateService.instant(requestOptions?.procesingMessage?.text ?? 'Notifications.ProcesingDetail'),
      0,
      false
    );

    if (requestOptions?.showLoading) {
      this.loadingService.showLoading = true;
    }

    return this.http.delete<T>(url, requestOptions?.clientOptions).pipe(
      tap(this.tabControl(requestOptions)),
      finalize(() => {
        this.notificationService.closeNotification(notificationId);

        if (requestOptions?.showLoading) {
          this.loadingService.showLoading = false;
        }
      })
    );
  }

  private tabControl = (requestOptions?: RequestOptions) => ({
    next: () => this.success(requestOptions?.successMessage),
    error: (err: HttpErrorResponse) => this.error(err, requestOptions?.responseStatusMessage)
  });

  private success(message?: MessageModal): void {
    if (message) {
      this.notificationService.success(
        this.translateService.instant(message.title ?? 'Notifications.Success'),
        this.translateService.instant(message.text ?? ''),
        DEFAULT_TIMEOUT_NOTIFICATIONS
      );
    }
  }

  private error(err: HttpErrorResponse, responseStatusMessage?: GenericDataInfo<MessageModal>): void {
    if (responseStatusMessage) {
      let title;
      let message: string;

      switch (err.status) {
        case HttpStatus.notFound:
        case HttpStatus.conflict:
          title = responseStatusMessage[err.status].title ?? '';
          message = responseStatusMessage[err.status].text as string;
          break;
        default:
          message = 'Notifications.GeneralError';
      }

      this.notificationService.error(
        this.translateService.instant(title ?? 'Notifications.Error'),
        message?.trim() ? this.translateService.instant(message) : message,
        DEFAULT_TIMEOUT_NOTIFICATIONS,
        true
      );
    }
  }
}
