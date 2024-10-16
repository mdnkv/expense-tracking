import { Routes } from '@angular/router';

import {IsAuthenticatedGuard} from "./auth/guards/auth.guards";

import {SignupViewComponent} from "./users/views/signup-view/signup-view.component";
import {LoginViewComponent} from "./auth/views/login-view/login-view.component";
import {UpdateUserViewComponent} from "./users/views/update-user-view/update-user-view.component";
import {UpdatePasswordViewComponent} from "./users/views/update-password-view/update-password-view.component";
import {CategoriesViewComponent} from "./categories/views/categories-view/categories-view.component";
import {EditCategoryViewComponent} from "./categories/views/edit-category-view/edit-category-view.component";
import {AccountsViewComponent} from "./accounts/views/accounts-view/accounts-view.component";
import {EditAccountViewComponent} from "./accounts/views/edit-account-view/edit-account-view.component";
import {OperationsViewComponent} from "./operations/views/operations-view/operations-view.component";
import {EditOperationViewComponent} from "./operations/views/edit-operation-view/edit-operation-view.component";

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
  },
  {
    path: 'categories',
    component: CategoriesViewComponent,
    canActivate: [IsAuthenticatedGuard()]
  },
  {
    path: 'category/:id',
    component: EditCategoryViewComponent,
    canActivate: [IsAuthenticatedGuard()]
  },
  {
    path: 'accounts',
    component: AccountsViewComponent,
    canActivate: [IsAuthenticatedGuard()]
  },
  {
    path: 'account/:id',
    component: EditAccountViewComponent,
    canActivate: [IsAuthenticatedGuard()]
  },
  {
    path: 'operations',
    component: OperationsViewComponent,
    canActivate: [IsAuthenticatedGuard()]
  },
  {
    path: 'operation/:id',
    component: EditOperationViewComponent,
    canActivate: [IsAuthenticatedGuard()]
  }
];
