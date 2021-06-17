import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { MaterialModule } from './modules/material/material.module';
import { UserComponent } from './components/user/user.component';
import { AdminComponent } from './components/admin/admin.component';
import { ModComponent } from './components/mod/mod.component';
import { AuthenticationModule } from './authentication/modules/authentication/authentication.module';

export const AUTHENTICATION_CONFIG = { authEndpoint: 'http://localhost:8080/api/auth', initialPage: 'home' };

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    UserComponent,
    AdminComponent,
    ModComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    AuthenticationModule.forRoot(AUTHENTICATION_CONFIG)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
