import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { NxDialogService, NxModalRef } from '@aposin/ng-aquila/modal';
import { Subscription } from 'rxjs';
import { UiAlertService } from 'src/app/core/services/alert.service';
import { AlertModel } from '../../models/alert.model';

@Component({
  selector: 'ui-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss']
})
export class UiAlertComponent implements OnInit, OnDestroy {
  @ViewChild('alertBody') templateAlertRef!: TemplateRef<any>;

  public alert?: AlertModel;
  public alertSubscription!: Subscription;

  private templateAlertDialogRef!: NxModalRef<any>;

  constructor(private dialogService: NxDialogService, private alertService: UiAlertService) {}

  ngOnInit() {
    this.alertSubscription = this.alertService.onAlert().subscribe((alert?: AlertModel) => {
      if (alert) {
        this.alert = alert;
        this.openDetailsModal();
      }
    });
  }

  ngOnDestroy(): void {
    this.alertSubscription.unsubscribe();
  }

  public actionAndClose(action: () => void) {
    this.closeDetailsModal();
    action();
  }

  public openDetailsModal() {
    this.templateAlertDialogRef = this.dialogService.open(this.templateAlertRef);
  }

  public closeDetailsModal() {
    this.templateAlertDialogRef.close();
    this.alertService.closeAlert();
  }
}
