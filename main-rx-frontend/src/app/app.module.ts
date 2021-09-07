import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TodosComponent } from './components/todos/todos.component';
import { MaterialUiModule } from './modules/material-ui.module';
import { DialogBoxComponent } from './components/dialog-box/dialog-box.component';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { LayoutComponent } from './components/layout/layout.component';
import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { LogoutComponent } from './components/auth/logout/logout.component';
import { AUTH_INTERCEPTOR_PROVIDER } from './components/auth/interceptors/token.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    TodosComponent,
    DialogBoxComponent,
    LoginComponent,
    RegisterComponent,
    LayoutComponent,
    ToolbarComponent,
    LogoutComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialUiModule,
  ],
  providers: [
    AUTH_INTERCEPTOR_PROVIDER
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
