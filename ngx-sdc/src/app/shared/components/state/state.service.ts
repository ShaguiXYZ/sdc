import { Injectable } from '@angular/core';
import { IComponentStateModel } from 'src/app/core/models';
import { AnalysisService } from 'src/app/core/services';

@Injectable()
export class StateService {
  constructor(private analysisService: AnalysisService) {}

  async componetCoverage(componentId: number): Promise<number> {
    const state = await this.analysisService.componentState(componentId);
    return state.coverage;
  }
}
