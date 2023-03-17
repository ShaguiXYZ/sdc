// Angular
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';

// Ndbx
import { NdbxIconModule } from '@allianz/ngx-ndbx/icon';
import { NxGridModule } from '@aposin/ng-aquila/grid';

import { TranslateModule } from '@ngx-translate/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UiCoreComponentsModule } from './core/components/core-components.module';
import { TRANSLATE_MODULE_CONFIG } from './shared/config/translate-utils';
import { NX_HEADER_CONFIG } from './core/components/header/models';
import { SDC_HEADER_MENU } from './shared/config/menu';

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
    UiCoreComponentsModule
  ],
  providers: [{ provide: NX_HEADER_CONFIG, useValue: { navigation: SDC_HEADER_MENU } }],
  bootstrap: [AppComponent]
})
export class AppModule {}
