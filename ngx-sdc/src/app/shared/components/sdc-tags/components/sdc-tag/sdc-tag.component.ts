import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { ITagModel } from 'src/app/core/models/sdc';

@Component({
  selector: 'sdc-tag',
  templateUrl: './sdc-tag.component.html',
  styleUrls: ['./sdc-tag.component.scss'],
  standalone: true,
  imports: [CommonModule]
})
export class SdcTagComponent implements OnInit {
  @Input()
  public data!: ITagModel;

  ngOnInit() {
    // init tag
  }
}
