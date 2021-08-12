import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/auth/login/login.component';
import { LogoutComponent } from './components/auth/logout/logout.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { TodosComponent } from './components/todos/todos.component';
import { AuthGuard } from './guard/auth.guard';

const routes: Routes = [
  { path: 'todo', component: TodosComponent, canActivate: [ AuthGuard ] },
  { path: 'logout', component: LogoutComponent, canActivate: [ AuthGuard ] },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: RegisterComponent },
  { path: '', redirectTo: 'todo', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
