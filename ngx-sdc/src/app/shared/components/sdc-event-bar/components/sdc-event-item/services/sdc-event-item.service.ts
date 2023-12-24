import { Injectable } from '@angular/core';
import { IComponentModel } from 'src/app/core/models/sdc';
import { ContextDataService } from 'src/app/core/services';
import { ComponentService } from 'src/app/core/services/sdc';
import { ContextDataInfo } from 'src/app/shared/constants';
import { SdcMetricsContextData } from 'src/app/shared/models';

@Injectable()
export class SdcEventItemService {
  constructor(
    private readonly componentSercice: ComponentService,
    private readonly contextDataService: ContextDataService
  ) {}

  public getComponent(componentId: number): Promise<IComponentModel> {
    return this.componentSercice.component(componentId).then(component => {
      this.contextDataService.set<SdcMetricsContextData>(ContextDataInfo.METRICS_DATA, { component });
      return component;
    });
  }
}
