import { Component, Input, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { UiContextDataService } from 'src/app/core/services';
import { ContextDataInfo } from '../../constants/context-data';
import { IBreadcrumbConfigModel } from './models';

@Component({
  selector: 'sdc-breadcrumb',
  templateUrl: './sdc-breadcrumb.component.html',
  styleUrls: ['./sdc-breadcrumb.component.scss']
})
export class SdcBreadcrumbComponent implements OnInit {
  public breadcrumbConfig?: IBreadcrumbConfigModel;

  constructor(private contextDataService: UiContextDataService, private location: Location) {}

  ngOnInit(): void {
    this.breadcrumbConfig = this.contextDataService.getContextData(ContextDataInfo.BREADCRUMBS_DATA);
  }

  backClicked() {
    this.location.back();
  }
}
