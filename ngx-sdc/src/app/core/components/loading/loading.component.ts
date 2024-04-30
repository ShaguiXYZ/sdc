import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { NxDialogService, NxModalModule, NxModalRef } from '@aposin/ng-aquila/modal';
import { LoadingService } from '@shagui/ng-shagui/core';
import { Subscription } from 'rxjs';

@Component({
  selector: 'nx-loading',
  styleUrls: ['./loading.component.scss'],
  templateUrl: './loading.component.html',
  standalone: true,
  imports: [CommonModule, NxModalModule]
})
export class LoadingComponent implements OnInit, OnDestroy {
  @ViewChild('loadingBody')
  private templateLoadingRef!: TemplateRef<any>;
  private templateLoadingDialogRef!: NxModalRef<any>;
  private loadingObs!: Subscription;

  constructor(
    private dialogService: NxDialogService,
    private loadingService: LoadingService
  ) {}

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
