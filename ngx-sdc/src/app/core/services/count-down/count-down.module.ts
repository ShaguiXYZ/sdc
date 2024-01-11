import { ModuleWithProviders, NgModule } from '@angular/core';
import { CountDownService } from './count-down.service';
import { DEFAULT_SEED, NX_COUNT_DOWN_SEED } from './models';

@NgModule({
  imports: [],
  declarations: [],
  providers: []
})
export class CountDownModule {
  public static forRoot(): ModuleWithProviders<CountDownModule> {
    return {
      ngModule: CountDownModule,
      providers: [CountDownService, { provide: NX_COUNT_DOWN_SEED, useValue: DEFAULT_SEED() }]
    };
  }
}
