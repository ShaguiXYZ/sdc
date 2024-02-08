import { CommonModule, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NxBreadcrumbModule } from '@aposin/ng-aquila/breadcrumb';
import { TranslateModule } from '@ngx-translate/core';
import { ContextDataService } from 'src/app/core/services';
import { ContextDataInfo } from '../../constants/context-data';
import { IBreadcrumbConfigModel } from './models';

@Component({
  selector: 'sdc-breadcrumb',
  styleUrls: ['./sdc-breadcrumb.component.scss'],
  templateUrl: './sdc-breadcrumb.component.html',
  standalone: true,
  imports: [CommonModule, NxBreadcrumbModule, SdcBreadcrumbComponent, TranslateModule]
})
export class SdcBreadcrumbComponent implements OnInit {
  public breadcrumbConfig?: IBreadcrumbConfigModel;

  constructor(
    private contextDataService: ContextDataService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.breadcrumbConfig = this.contextDataService.get(ContextDataInfo.BREADCRUMBS_DATA);
  }

  backClicked() {
    this.location.back();
  }
}
