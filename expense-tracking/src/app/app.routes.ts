import { Routes } from '@angular/router';
import {SignupViewComponent} from "./users/views/signup-view/signup-view.component";
import {LoginViewComponent} from "./auth/views/login-view/login-view.component";
import {UpdateUserViewComponent} from "./users/views/update-user-view/update-user-view.component";
import {UpdatePasswordViewComponent} from "./users/views/update-password-view/update-password-view.component";
import {IsAuthenticatedGuard} from "./auth/guards/auth.guards";

export const routes: Routes = [
  {
    path: 'auth/login',
    component: LoginViewComponent
  },
  {
    path: 'users/signup',
    component: SignupViewComponent
  },
  {
    path: 'users/user',
    component: UpdateUserViewComponent,
    canActivate: [IsAuthenticatedGuard()]
  },
  {
    path: 'users/password',
    component: UpdatePasswordViewComponent,
    canActivate: [IsAuthenticatedGuard()]
  }
];
