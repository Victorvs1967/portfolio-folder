import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './authentication/components/login/login.component';
import { LogoutComponent } from './authentication/components/logout/logout.component';
import { RegisterComponent } from './authentication/components/register/register.component';
import { AdminComponent } from './components/admin/admin.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { HomeComponent } from './components/home/home.component';
import { ModComponent } from './components/mod/mod.component';
import { PostsComponent } from './components/posts/posts.component';
import { UserFormComponent } from './components/user-form/user-form.component';
import { UserComponent } from './components/user/user.component';

const routes: Routes = [
  { path: "home", component: HomeComponent },
  { path: "user", component: UserComponent },
  { path: "admin", component: AdminComponent },
  { path: "edit/:username", component: UserFormComponent },
  { path: "mod", component: ModComponent },
  { path: "login", component: LoginComponent },
  { path: "logout", component: LogoutComponent },
  { path: "register", component: RegisterComponent },
  { path: "posts", component: PostsComponent },
  { path: "post", component: CreatePostComponent },
  { path: "", redirectTo: "home", pathMatch: "full" },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
