import { OverlayModule } from '@angular/cdk/overlay';
import { CommonModule } from '@angular/common';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { NxAutocompleteModule } from '@aposin/ng-aquila/autocomplete';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxCardModule } from '@aposin/ng-aquila/card';
import { NxFormfieldModule } from '@aposin/ng-aquila/formfield';
import { NxInputModule } from '@aposin/ng-aquila/input';
import { TranslateModule } from '@ngx-translate/core';
import { Observable, Subscription, of } from 'rxjs';
import { emptyFn } from 'src/app/core/lib';
import { ComponentFormService } from './services';

@Component({
  selector: 'sdc-component-form',
  styleUrls: ['./sdc-component-form.component.scss'],
  templateUrl: './sdc-component-form.component.html',
  providers: [ComponentFormService],
  standalone: true,
  imports: [
    CommonModule,
    NxAutocompleteModule,
    NxButtonModule,
    NxCardModule,
    NxFormfieldModule,
    NxInputModule,
    OverlayModule,
    ReactiveFormsModule,
    TranslateModule
  ]
})
export class SdcComponentFormComponent implements OnInit, OnDestroy {
  @Input()
  public id?: number;
  public form!: FormGroup;

  private subscriptions$: Subscription[] = [];

  constructor(private formService: ComponentFormService) {}

  ngOnInit(): void {
    this.createForm();

    this.formService.onDataChange().subscribe(data => {
      this.form.patchValue(data);
    });

    this.id && this.formService.getComponent(this.id);
  }

  ngOnDestroy(): void {
    this.subscriptions$.forEach(subscription => subscription.unsubscribe());
  }

  public onSubmit(): void {
    this.formService.saveComponent(this.form.value);
  }

  public dataListByName(name: string): (key: string) => Observable<string[]> {
    return this.formService.dataListByName(name);
  }

  private createForm(): void {
    this.form = new FormGroup({
      componentData: new FormGroup({
        name: new FormControl('', [Validators.required]),
        description: new FormControl('')
      }),
      componentTypeArchitecture: new FormGroup({
        componentType: new FormControl('', [Validators.required], [this.componentTypeArchitectureValidator('component_types')]),
        network: new FormControl('', [Validators.required], [this.componentTypeArchitectureValidator('networks')]),
        deploymentType: new FormControl('', [Validators.required], [this.componentTypeArchitectureValidator('deployment_types')]),
        platform: new FormControl('', [Validators.required], [this.componentTypeArchitectureValidator('platforms')]),
        architecture: new FormControl('', [Validators.required], [this.componentTypeArchitectureValidator('architectures')]),
        language: new FormControl('', [Validators.required], [this.componentTypeArchitectureValidator('languages')])
      }),
      properties: new FormGroup({}),
      uris: new FormGroup({})
    });

    this.subscriptions$.push(this.form.valueChanges.subscribe(data => emptyFn()));
  }

  private componentTypeArchitectureValidator =
    (name: string) =>
    async (control: AbstractControl): Promise<ValidationErrors | null> => {
      const data = await this.formService.getDataList(name);
      const valid = data.includes(control.value);

      return valid ? null : { invalid: true, field: name };
    };
}
