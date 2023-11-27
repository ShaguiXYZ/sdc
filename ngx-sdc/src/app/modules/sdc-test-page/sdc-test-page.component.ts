import { Component, OnInit } from '@angular/core';
import { ITagModel } from 'src/app/core/models/sdc';
import { TagService } from 'src/app/core/services/sdc';
import { SdcTagsComponent } from 'src/app/shared/components';
import { SdcComponentFormComponent } from 'src/app/shared/components/sdc-forms/sdc-component-form';

@Component({
  selector: 'sdc-test-page',
  templateUrl: './sdc-test-page.component.html',
  styleUrls: ['./sdc-test-page.component.scss'],
  standalone: true,
  imports: [SdcComponentFormComponent, SdcTagsComponent]
})
export class SdcTestComponent implements OnInit {
  public tags: ITagModel[] = [];
  constructor(private readonly tagService: TagService) {}

  ngOnInit(): void {
    this.tagService.tags().then(data => {
      this.tags = data.page;
    });
  }
}
