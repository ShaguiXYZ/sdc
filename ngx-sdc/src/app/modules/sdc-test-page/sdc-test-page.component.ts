import { Component } from '@angular/core';
import { SdcComponentFormComponent } from 'src/app/shared/components/sdc-forms/sdc-component-form';

@Component({
  selector: 'sdc-test-page',
  templateUrl: './sdc-test-page.component.html',
  styleUrls: ['./sdc-test-page.component.scss'],
  standalone: true,
  imports: [SdcComponentFormComponent]
})
export class SdcTestComponent {}
