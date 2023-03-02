import { Injectable } from '@angular/core';
import { AnalysisService, ComponentService } from 'src/app/core/services';

@Injectable()
export class StateService {
  constructor(private componentService: ComponentService) {}

  async componetCoverage(componentId: number): Promise<number> {
    const state = await this.componentService.componentState(componentId);
    return state.coverage;
  }
}
