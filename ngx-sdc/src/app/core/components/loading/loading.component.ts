import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { NxDialogService, NxModalModule, NxModalRef } from '@aposin/ng-aquila/modal';
import { NxSpinnerModule } from '@aposin/ng-aquila/spinner';
import { Subscription } from 'rxjs';
import { LoadingService } from './services';

@Component({
  selector: 'nx-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss'],
  standalone: true,
  imports: [CommonModule, NxModalModule, NxSpinnerModule]
})
export class LoadingComponent implements OnInit, OnDestroy {
  @ViewChild('loadingBody') templateLoadingRef!: TemplateRef<any>;
  private templateLoadingDialogRef!: NxModalRef<any>;

  private loadingObs!: Subscription;

  constructor(private dialogService: NxDialogService, private loadingService: LoadingService) {}

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
