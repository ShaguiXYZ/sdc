import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxDialogService, NxModalModule, NxModalRef } from '@aposin/ng-aquila/modal';
import { Subscription } from 'rxjs';
import { AlertModel } from './models';
import { AlertService } from './services';

@Component({
  selector: 'nx-alert',
  styleUrls: ['./alert.component.scss'],
  templateUrl: './alert.component.html',
  standalone: true,
  imports: [CommonModule, NxButtonModule, NxCopytextModule, NxHeadlineModule, NxModalModule]
})
export class AlertComponent implements OnInit, OnDestroy {
  public alert?: AlertModel;
  public alertSubscription!: Subscription;

  @ViewChild('alertBody')
  private templateAlertRef!: TemplateRef<any>;
  private templateAlertDialogRef!: NxModalRef<any>;

  constructor(
    private dialogService: NxDialogService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
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

  public actionAndClose(action: () => void): void {
    this.closeDetailsModal();
    action();
  }

  public openDetailsModal(): void {
    this.templateAlertDialogRef = this.dialogService.open(this.templateAlertRef);
  }

  public closeDetailsModal(): void {
    this.templateAlertDialogRef.close();
    this.alertService.closeAlert();
  }
}
