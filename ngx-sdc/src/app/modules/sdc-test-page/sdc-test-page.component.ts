import { Component, OnInit } from '@angular/core';
import { SdcComponentFormComponent } from 'src/app/shared/components/sdc-forms/sdc-component-form';
import { SdcTestRoutingModule } from './sdc-test-page-routing.module';

@Component({
  selector: 'sdc-test-page',
  templateUrl: './sdc-test-page.component.html',
  styleUrls: ['./sdc-test-page.component.scss'],
  standalone: true,
  imports: [SdcTestRoutingModule, SdcComponentFormComponent]
})
export class SdcTestComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
