import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { NxFormfieldModule } from '@aposin/ng-aquila/formfield';
import { NxInputModule } from '@aposin/ng-aquila/input';
import { TranslateModule } from '@ngx-translate/core';
import { ITagModel } from 'src/app/core/models/sdc';
import { SdcTagComponent } from '../sdc-tag/sdc-tag.component';
import { ViewType, defaultViewType } from './models';

@Component({
  selector: 'sdc-tags',
  styleUrls: ['./sdc-tags.component.scss'],
  templateUrl: './sdc-tags.component.html',
  standalone: true,
  imports: [CommonModule, FormsModule, NxFormfieldModule, NxInputModule, ReactiveFormsModule, SdcTagComponent, TranslateModule]
})
export class SdcTagsComponent implements OnInit {
  public form!: FormGroup;

  @Input()
  public removable: boolean = false;

  @Input()
  public viewType: ViewType = defaultViewType;

  @Output()
  public add: EventEmitter<ITagModel> = new EventEmitter<ITagModel>();

  @Output()
  public remove: EventEmitter<ITagModel> = new EventEmitter<ITagModel>();

  @ViewChild('nameInput', { static: true })
  private nameInput!: ElementRef;

  // pattern for name input allowing only letters and numbers and _ beguining with a letter
  private namePattern: RegExp = /^[a-zA-Z]\w*$/;
  private _tags: ITagModel[] = [];

  ngOnInit() {
    this.createForm();
  }

  public get tags(): ITagModel[] {
    return this._tags;
  }
  @Input()
  // Return a copy of the tags array ordered by name
  public set tags(value: ITagModel[]) {
    const sortedTags = value.slice().sort((a, b) => a.name.localeCompare(b.name));
    this._tags = sortedTags;
  }

  public focusNameInput(): void {
    this.nameInput.nativeElement.focus();
  }

  public onAddTag = (): void => {
    this.form.valid && this.populateForm();
  };

  public onBlurTag = (): void => {
    this.form.value.name = '';
    this.nameInput.nativeElement.blur();
  };

  public omit_special_char(event: KeyboardEvent, control: string): void {
    event.key.length === 1 && !this.namePattern.test(this.form.controls[control].value + event.key) && event.preventDefault();
  }

  public onRemoveTag = (tag: ITagModel): void => {
    this.remove.emit(tag);
  };

  private createForm(): void {
    this.form = new FormGroup({
      name: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(16),
        Validators.pattern(this.namePattern)
      ])
    });
  }

  private populateForm(): void {
    this.add.emit(this.form.value);
    this.form.reset();
  }
}
