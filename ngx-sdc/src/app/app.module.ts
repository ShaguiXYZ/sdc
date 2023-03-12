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
import { UiHeaderModule } from './modules/header/header.module';
import { NX_HEADER_CONFIG } from './modules/header';
import { SDC_HEADER_MENU } from './shared/config/app.constants';

@NgModule({
  declarations: [AppComponent],
  imports: [
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    NxGridModule,
    NdbxIconModule,
    HttpClientModule,
    RouterModule,
    TranslateModule.forRoot(TRANSLATE_MODULE_CONFIG),

    UiHeaderModule
  ],
  providers: [{ provide: NX_HEADER_CONFIG, useValue: { navigation: SDC_HEADER_MENU } }],
  bootstrap: [AppComponent]
})
export class AppModule {}
