import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { NxDialogService, NxModalRef } from '@aposin/ng-aquila/modal';
import { Subscription } from 'rxjs';
import { UiLoadingService } from './services';

@Component({
  selector: 'nx-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss']
})
export class UiLoadingComponent implements OnInit, OnDestroy {
  @ViewChild('loadingBody') templateLoadingRef!: TemplateRef<any>;
  private templateLoadingDialogRef!: NxModalRef<any>;

  private loadingObs!: Subscription;

  constructor(private dialogService: NxDialogService, private loadingService: UiLoadingService) {}

  ngOnInit(): void {
    this.loadingObs = this.loadingService.uiShowLoading.subscribe((show: boolean) => {
      if (show) {
        this.openLoadingModal();
      } else {
        this.closeLoadingModal();
      }
    });
  }

  ngOnDestroy(): void {
    this.loadingObs.unsubscribe();
  }

  private openLoadingModal(): void {
    this.templateLoadingDialogRef = this.dialogService.open(this.templateLoadingRef, {
      width: '200px',
      disableClose: true
    });
  }

  private closeLoadingModal(): void {
    this.templateLoadingDialogRef?.close();
  }
}