import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './authentication/components/login/login.component';
import { LogoutComponent } from './authentication/components/logout/logout.component';
import { RegisterComponent } from './authentication/components/register/register.component';
import { AdminComponent } from './components/admin/admin.component';
import { HomeComponent } from './components/home/home.component';
import { ModComponent } from './components/mod/mod.component';
import { UserComponent } from './components/user/user.component';

const routes: Routes = [
  { path: "home", component: HomeComponent },
  { path: "user", component: UserComponent },
  { path: "admin", component: AdminComponent },
  { path: "mod", component: ModComponent },
  { path: "login", component: LoginComponent },
  { path: "logout", component: LogoutComponent },
  { path: "register", component: RegisterComponent },
  { path: "", redirectTo: "home", pathMatch: "full" },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }