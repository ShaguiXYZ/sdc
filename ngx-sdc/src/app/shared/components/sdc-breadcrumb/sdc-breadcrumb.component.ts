import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ContextDataService } from 'src/app/core/services';
import { ContextDataInfo } from '../../constants/context-data';
import { IBreadcrumbConfigModel } from './models';

@Component({
  selector: 'sdc-breadcrumb',
  templateUrl: './sdc-breadcrumb.component.html',
  styleUrls: ['./sdc-breadcrumb.component.scss']
})
export class SdcBreadcrumbComponent implements OnInit {
  public breadcrumbConfig?: IBreadcrumbConfigModel;

  constructor(private contextDataService: ContextDataService, private location: Location) {}

  ngOnInit(): void {
    this.breadcrumbConfig = this.contextDataService.get(ContextDataInfo.BREADCRUMBS_DATA);
  }

  backClicked() {
    this.location.back();
  }
}
