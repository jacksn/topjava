import {RouterModule, Routes} from "@angular/router";
import {ModuleWithProviders} from "@angular/core";
import {LoginComponent} from "./component/auth/login.component";
import {RegisterComponent} from "./component/user/register.component";
import {MealListComponent} from "./component/meal/meal-list.component";
import {AuthActivateGuard} from "./shared/auth.activate.guard";
import {ProfileComponent} from "./component/user/profile.component";
import {UserListComponent} from "./component/user/user-list.component";
const appRoutes: Routes = [
  {
    path: "",
    pathMatch: "full",
    redirectTo: "/meal-list",
  },
  {
    path: "login",
    component: LoginComponent,
  },
  {
    path: "register",
    component: RegisterComponent
  },
  {
    path: "meal-list",
    component: MealListComponent,
    canActivate: [AuthActivateGuard],
  },
  {
    path: "profile",
    component: ProfileComponent,
    canActivate: [AuthActivateGuard]
  },
  {
    path: "user-list",
    component: UserListComponent,
    canActivate: [AuthActivateGuard]
  }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes, {useHash: true});
