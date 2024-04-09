import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { ButtonModel, MessageModal, TypeButton } from '@shagui/ng-shagui/core';
import { Observable, Subject } from 'rxjs';
import { AlertModel } from 'src/app/core/components/alert/models/alert.model';

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  private alert$ = new Subject<AlertModel | undefined>();

  constructor(private translateService: TranslateService) {}

  // enable subscribing to alerts observable
  public onAlert(): Observable<AlertModel | undefined> {
    return this.alert$.asObservable();
  }

  public alert(alert: AlertModel): void {
    this.alert$.next(alert);
  }

  public closeAlert(): void {
    this.alert$.next(undefined);
  }

  public confirm(
    message: MessageModal,
    callback?: () => void,
    labels: { okText?: string; cancelText?: string } = { okText: 'Continue', cancelText: 'Cancel' },
    reserse?: boolean
  ): void {
    const _labels = { ...{ okText: 'Continue', cancelText: 'Cancel' }, ...labels };
    let buttons: ButtonModel[] = [];

    // eslint-disable-next-line @typescript-eslint/no-empty-function
    buttons.push(new ButtonModel(TypeButton.primary, this.translateService.instant(_labels.cancelText), () => {}));

    if (callback) {
      buttons.push(
        new ButtonModel(TypeButton.secondary, this.translateService.instant(_labels.okText), () => {
          callback();
        })
      );
    }

    if (reserse) {
      buttons = buttons.reverse();
    }

    const titleTranslate = message.title ? this.translateService.instant(message.title) : '';
    let messageTranslate: string | string[];

    if (Array.isArray(message.text)) {
      messageTranslate = message.text.map(str => (str ? this.translateService.instant(str) : ''));
    } else {
      messageTranslate = message.text ? this.translateService.instant(message.text) : '';
    }

    const alert: AlertModel = new AlertModel(titleTranslate, messageTranslate, buttons);

    this.alert(alert);
  }
}
