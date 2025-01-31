import { CommonModule } from '@angular/common';
import { Component, inject, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { NxDialogService, NxModalModule, NxModalRef } from '@aposin/ng-aquila/modal';
import { LoadingService } from '@shagui/ng-shagui/core';
import { Subscription } from 'rxjs';

@Component({
    selector: 'nx-loading',
    styleUrls: ['./loading.component.scss'],
    templateUrl: './loading.component.html',
    imports: [CommonModule, NxModalModule]
})
export class LoadingComponent implements OnInit, OnDestroy {
  @ViewChild('loadingBody')
  private templateLoadingRef!: TemplateRef<void>;
  private templateLoadingDialogRef!: NxModalRef<void>;
  private loadingObs!: Subscription;

  private readonly loadingService = inject(LoadingService);

  constructor(private readonly dialogService: NxDialogService) {}

  ngOnInit(): void {
    this.loadingObs = this.loadingService
      .asObservable()
      .subscribe((show: boolean) => (show ? this.openLoadingModal() : this.closeLoadingModal()));
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
