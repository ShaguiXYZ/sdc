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
  public state?: 'info' | 'success' | 'warning' | 'error';

  @Output()
  public remove: EventEmitter<ITagModel> = new EventEmitter<ITagModel>();

  ngOnInit() {
    this.state = !this.state && this.data.analysisTag ? 'success' : 'info';
  }

  public onRemove(): void {
    this.remove.emit(this.data);
  }
}
