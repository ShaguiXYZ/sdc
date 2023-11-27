import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { ITagModel } from 'src/app/core/models/sdc';

@Component({
  selector: 'sdc-tag',
  templateUrl: './sdc-tag.component.html',
  styleUrls: ['./sdc-tag.component.scss'],
  standalone: true,
  imports: [CommonModule, TranslateModule]
})
export class SdcTagComponent implements OnInit {
  @Input()
  public disabled: boolean = false;

  @Input()
  public removable: boolean = false;

  @Input()
  public data!: ITagModel;

  @Input()
  public state: 'info' | 'success' | 'warning' | 'error' = 'info';

  @Output()
  public onRemove: EventEmitter<ITagModel> = new EventEmitter<ITagModel>();

  ngOnInit() {
    // init tag
  }

  public remove(): void {
    this.onRemove.emit(this.data);
  }
}
