import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TranslateService } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { TranslateServiceMock } from 'src/app/core/mock/services';
import { AlertComponent } from '../alert.component';

describe('AlertComponent', () => {
  let component: AlertComponent;
  let fixture: ComponentFixture<AlertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AlertComponent],
      providers: [{ provide: TranslateService, useClass: TranslateServiceMock }]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have alertSubscription property', () => {
    expect(component.alertSubscription).toBeDefined();
  });

  it('should have alertSubscription property of type Subscription', () => {
    expect(component.alertSubscription instanceof Subscription).toBeTrue();
  });
});
