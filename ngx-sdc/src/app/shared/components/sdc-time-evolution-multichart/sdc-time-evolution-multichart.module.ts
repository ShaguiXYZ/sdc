import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SdcTimeEvolutionChartModule } from '../sdc-charts';
import { SdcTimeEvolutionMultichartComponent } from './sdc-time-evolution-multichart.component';

@NgModule({
  declarations: [SdcTimeEvolutionMultichartComponent],
  imports: [CommonModule, SdcTimeEvolutionChartModule],
  exports: [SdcTimeEvolutionMultichartComponent]
})
export class SdcTimeEvolutionMultichartModule {}
