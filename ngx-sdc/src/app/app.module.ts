// Angular
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// Ndbx
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { NdbxIconModule } from '@allianz/ngx-ndbx/icon';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TranslateModule } from '@ngx-translate/core';
import { TRANSLATE_MODULE_CONFIG } from './shared/config/translate-utils';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule,
    AppRoutingModule,
    HttpClientModule,

    TranslateModule.forRoot(TRANSLATE_MODULE_CONFIG),

    NxGridModule,
    NdbxIconModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
