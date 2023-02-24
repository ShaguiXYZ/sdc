import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Observable, Subject } from 'rxjs';
import { MessageModal } from 'src/app/shared/interfaces/modal';
import { AlertModel } from 'src/app/shared/models/alertModel';
import { ButtonModel, TypeButton } from 'src/app/shared/models/buttonModel';

@Injectable({
  providedIn: 'root'
})
export class UiAlertService {
  private alert$ = new Subject<AlertModel | undefined>();

  constructor(private translateService: TranslateService) {}

  // enable subscribing to alerts observable
  public onAlert(): Observable<AlertModel | undefined> {
    return this.alert$.asObservable();
  }

  public alert(alert: AlertModel) {
    this.alert$.next(alert);
  }

  public closeAlert() {
    this.alert$.next(undefined);
  }

  public confirm(message: MessageModal, callback?: () => void, okText: string = 'Continue', cancelText: string = 'Cancel') {
    const buttons: ButtonModel[] = [];
    buttons.push(new ButtonModel(TypeButton.primary, this.translateService.instant(cancelText), () => {}));

    if (callback) {
      buttons.push(
        new ButtonModel(TypeButton.secondary, this.translateService.instant(okText), () => {
          callback();
        })
      );
    }

    const titleTranslate = message.title ? this.translateService.instant(message.title) : '';
    const messageTranslate = message.message ? this.translateService.instant(message.message) : '';

    const alert: AlertModel = new AlertModel(titleTranslate, messageTranslate, buttons);

    this.alert(alert);
  }
}
