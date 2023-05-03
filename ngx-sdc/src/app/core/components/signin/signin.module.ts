import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SigninRoutingModule } from './signin-routing.module';
import { SigninComponent } from './signin.component';

@NgModule({
  declarations: [SigninComponent],
  imports: [CommonModule, SigninRoutingModule],
  exports: [SigninComponent]
})
export class SigninModule {}
