import {APP_INITIALIZER, NgModule}      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent }  from './app.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {CalendarModule, DataTableModule, GrowlModule} from "primeng/primeng";
import {DatePipe} from "@angular/common";
import {HeaderComponent} from "./component/auth/header.component";
import {routing} from "./app.routes";
import {MealListComponent} from "./component/meal/meal-list.component";
import {EntryComponent} from "./component/auth/entry.component";
import {EditMealComponent} from "./component/meal/meal-edit.component";
import {ProfileComponent} from "./component/user/profile.component";
import {RegisterComponent} from "./component/user/register.component";
import {UserListComponent} from "./component/user/user-list.component";
import {UserEditComponent} from "./component/user/user-edit.component";
import {I18nPipe} from "./shared/pipe/i18n.pipe";
import {AuthService} from "./service/auth.service";
import {AuthActivateGuard} from "./shared/auth.activate.guard";
import {MealService} from "./service/meal.service";
import {UserService} from "./service/user.service";
import {ProfileService} from "./service/profile.service";
import {I18nService} from "./service/i18n.service";
import {DateTimeTransformer} from "./shared/date-time.transformer";
import {ExceptionService} from "./service/exception.service";
import {I18Enum} from "./model/i18n.enum";

@NgModule({
  imports:      [ BrowserModule, FormsModule, ReactiveFormsModule, HttpModule, routing, CalendarModule, DataTableModule, GrowlModule],
  declarations: [ AppComponent , MealListComponent, EntryComponent,
    EditMealComponent, HeaderComponent,
    ProfileComponent,
    RegisterComponent,
    UserListComponent,
    UserEditComponent,
    I18nPipe
  ],
  bootstrap:    [ AppComponent ],
  providers: [AuthService, AuthActivateGuard, MealService, UserService, ProfileService,
    I18nService, DateTimeTransformer, DatePipe, ExceptionService,
    {
      provide: APP_INITIALIZER,
      // useFactory: (i18NService: I18nService) => () => i18NService.initMessages(I18Enum.ru),
      // or
      useFactory: initApp,
      deps: [I18nService],
      multi: true
    }
  ]
})
export class AppModule { }

export function initApp(i18nService: I18nService) {
  // Do initing of services that is required before app loads
  // NOTE: this factory needs to return a function (that then returns a promise)
  return () => i18nService.initMessages(I18Enum.ru);  // + any other services...
}
