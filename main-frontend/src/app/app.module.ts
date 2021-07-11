import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { MaterialModule } from './modules/material.module';
import { UserComponent } from './components/user/user.component';
import { AdminComponent } from './components/admin/admin.component';
import { ModComponent } from './components/mod/mod.component';
import { AuthenticationModule } from './authentication/modules/authentication/authentication.module';
import { UsersTableComponent } from './components/users-table/users-table.component';
import { UserFormComponent } from './components/user-form/user-form.component';
import { environment } from 'src/environments/environment';
import { FooterComponent } from './components/footer/footer.component';
import { HomeComponent } from './components/home/home.component';
import { PostsComponent } from './components/posts/posts.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { UpdatePostComponent } from './components/update-post/update-post.component';
import { NgxEditorModule } from 'ngx-editor';
import { ViewPostComponent } from './components/view-post/view-post.component';

export const AUTHENTICATION_CONFIG = { authEndpoint: environment.authUrl, initialPage: 'home' };

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    UserComponent,
    AdminComponent,
    ModComponent,
    UsersTableComponent,
    UserFormComponent,
    HomeComponent,
    FooterComponent,
    PostsComponent,
    CreatePostComponent,
    UpdatePostComponent,
    ViewPostComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    NgxEditorModule,
    AuthenticationModule.forRoot(AUTHENTICATION_CONFIG)
  ],
  providers: [],
  bootstrap: [ AppComponent ],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class AppModule { }
