import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { ITagModel } from 'src/app/core/models/sdc';
import { SdcTagComponent } from './components';

@Component({
  selector: 'sdc-tags',
  templateUrl: './sdc-tags.component.html',
  styleUrls: ['./sdc-tags.component.scss'],
  standalone: true,
  imports: [CommonModule, SdcTagComponent]
})
export class SdcTagsComponent implements OnInit {
  @Input()
  public tags: ITagModel[] = [];

  constructor() {}

  ngOnInit() {
    console.log(this.tags);
  }
}
