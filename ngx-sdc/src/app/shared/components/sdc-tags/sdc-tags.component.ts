import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { NxFormfieldModule } from '@aposin/ng-aquila/formfield';
import { NxInputModule } from '@aposin/ng-aquila/input';
import { TranslateModule } from '@ngx-translate/core';
import { ITagModel } from 'src/app/core/models/sdc';
import { SdcTagComponent } from './components';

@Component({
  selector: 'sdc-tags',
  templateUrl: './sdc-tags.component.html',
  styleUrls: ['./sdc-tags.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, NxFormfieldModule, NxInputModule, ReactiveFormsModule, SdcTagComponent, TranslateModule]
})
export class SdcTagsComponent implements OnInit {
  @ViewChild('nameInput', { static: true }) nameInput!: ElementRef;

  public form!: FormGroup;

  @Input()
  public removable: boolean = false;

  @Input()
  public tags: ITagModel[] = [];

  @Output()
  public onAdd: EventEmitter<ITagModel> = new EventEmitter<ITagModel>();

  @Output()
  public onRemove: EventEmitter<ITagModel> = new EventEmitter<ITagModel>();

  ngOnInit() {
    this.createForm();
  }

  public focusNameInput(): void {
    this.nameInput.nativeElement.focus();
  }

  public onAddTag = (): void => {
    this.form.valid && this.populateForm();
  };

  public onRemoveTag = (tag: ITagModel): void => {
    this.onRemove.emit(tag);
  };

  public omit_special_char(event: KeyboardEvent) {
    !/[a-zA-Z0-9]/.test(event.key) && event.preventDefault();
  }

  private createForm(): void {
    this.form = new FormGroup({
      name: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(16),
        Validators.pattern(/^[a-zA-Z0-9]+$/)
      ])
    });
  }

  private populateForm(): void {
    this.onAdd.emit(this.form.value);
    this.form.reset();
  }
}
