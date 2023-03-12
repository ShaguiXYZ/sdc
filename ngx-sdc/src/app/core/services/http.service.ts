import { HttpClient, HttpErrorResponse, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize, tap } from 'rxjs/operators';
import { UiLoadingService, UiNotificationService } from 'src/app/shared/components';
import { DEFAULT_TIMEOUT_NOTIFICATIONS, HttpStatus } from 'src/app/shared/config/app.constants';
import { GenericDataInfo } from 'src/app/shared/interfaces/dataInfo';
import { MessageModal } from 'src/app/shared/interfaces/modal';

export interface RequestOptions {
  // params?: any;
  showLoading?: boolean;
  successMessage?: MessageModal;
  responseStatusMessage?: GenericDataInfo<MessageModal>;
  clientOptions?: any;
}

export interface PageHttp<T> {
  totalItems: number;
  totalPages: number;
  currentPage: number;
  items: T | T[];
}

@Injectable({
  providedIn: 'root'
})
export class UiHttpHelper {
  constructor(
    private loadingService: UiLoadingService,
    private notificationService: UiNotificationService,
    private translateService: TranslateService,
    private http: HttpClient
  ) {}

  public get<T>(url: string, requestOptions?: RequestOptions): Observable<T | HttpEvent<T>> {
    if (requestOptions?.showLoading) {
      this.loadingService.showLoading = true;
    }

    return this.http.get<T>(url, requestOptions?.clientOptions).pipe(
      tap(this.tabControl(requestOptions)),
      finalize(() => {
        if (requestOptions?.showLoading) {
          this.loadingService.showLoading = false;
        }
      })
    );
  }

  public _post<T>(url: string, body?: T, requestOptions?: RequestOptions): Observable<T | HttpEvent<T>> {
    return this.post<T, T>(url, body, requestOptions);
  }

  public post<OUT, IN>(url: string, body?: IN, requestOptions?: RequestOptions): Observable<OUT | HttpEvent<OUT>> {
    const notificationId = this.notificationService.info(
      this.translateService.instant('Notifications.Saving'),
      this.translateService.instant('Notifications.SavingDetail'),
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
      this.translateService.instant('Notifications.Saving'),
      this.translateService.instant('Notifications.SavingDetail'),
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
      this.translateService.instant('Notifications.Deleting'),
      this.translateService.instant('Notifications.SavingDetail'),
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

  private success(message?: MessageModal): void {
    if (message) {
      this.notificationService.success(
        this.translateService.instant(message.title || 'Notifications.Success'),
        this.translateService.instant(message.message || ''),
        DEFAULT_TIMEOUT_NOTIFICATIONS
      );
    }
  }

  private error(err: HttpErrorResponse, responseStatusMessage?: GenericDataInfo<MessageModal>): void {
    if (responseStatusMessage) {
      let title;
      let message;

      switch (err.status) {
        case HttpStatus.notFound:
        case HttpStatus.conflict:
          title = responseStatusMessage[err.status].title;
          message = responseStatusMessage[err.status].message;
          break;
        default:
          message = 'Notifications.GeneralError';
      }

      this.notificationService.error(
        this.translateService.instant(title || 'Notifications.Error'),
        this.translateService.instant(message || ''),
        DEFAULT_TIMEOUT_NOTIFICATIONS,
        true
      );
    }
  }

  private tabControl = (requestOptions?: RequestOptions) => ({
    next: () => this.success(requestOptions?.successMessage),
    error: (err: HttpErrorResponse) => this.error(err, requestOptions?.responseStatusMessage)
  });
}
