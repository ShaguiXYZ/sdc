import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxDialogService, NxModalModule, NxModalRef } from '@aposin/ng-aquila/modal';
import { Subscription } from 'rxjs';
import { AlertModel } from './models';
import { UiAlertService } from './services';

@Component({
  selector: 'nx-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss'],
  standalone: true,
  imports: [CommonModule, NxButtonModule, NxCopytextModule, NxHeadlineModule, NxModalModule]
})
export class UiAlertComponent implements OnInit, OnDestroy {
  @ViewChild('alertBody') templateAlertRef!: TemplateRef<any>;

  public alert?: AlertModel;
  public alertSubscription!: Subscription;

  private templateAlertDialogRef!: NxModalRef<any>;

  constructor(private dialogService: NxDialogService, private alertService: UiAlertService) {}

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
