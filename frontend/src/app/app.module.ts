import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {LoginContainerComponent} from "./features/login-container/login-container.component";
import {LoginComponent} from "./features/login/login.component";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatCardModule} from "@angular/material/card";
import {MatToolbarModule} from "@angular/material/toolbar";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatTabsModule} from "@angular/material/tabs";
import {MatRadioModule} from "@angular/material/radio";
import {MatSelectModule} from "@angular/material/select";
import {MatNativeDateModule} from "@angular/material/core";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {FlexLayoutModule} from "@angular/flex-layout";
import {RegisterComponent} from "./features/register/register.component";
import {RegisterContainerComponent} from "./features/register-container/register-container.component";
import { ClientPageComponent } from './features/client-page/client-page.component';
import {MatTableModule} from "@angular/material/table";
import {AuthenticationService} from "./features/authentication/authentication.service";
import {JwtModule} from "@auth0/angular-jwt";
import {tokenGetter} from "./features/authentication/auth.module";
import {environment} from "./environment/environment";
import { AdminpageComponent } from './features/adminpage/adminpage.component';
import { UsersPageComponent } from './features/users-page/users-page.component';
import { DevicesPageComponent } from './features/devices-page/devices-page.component';
import { MappingsPageComponent } from './features/mappings-page/mappings-page.component';
import {MatIconModule} from "@angular/material/icon";
import { UpdateUserComponent } from './features/update-user/update-user.component';
import {MatDialogModule} from "@angular/material/dialog";
import { CreateUserComponent } from './features/create-user/create-user.component';
import { CreateDeviceComponent } from './features/create-device/create-device.component';
import { UpdateDeviceComponent } from './features/update-device/update-device.component';
import { UpdateMappingComponent } from './features/update-mapping/update-mapping.component';
import { ChartComponent } from './features/chart/chart.component';
import {LineChartModule} from "@swimlane/ngx-charts";
import {DatePipe} from "@angular/common";
import {ChatComponent} from "./features/chat/chat.component";
import { ChatClientComponent } from './features/chat-client/chat-client.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LoginContainerComponent,
    RegisterComponent,
    RegisterContainerComponent,
    ClientPageComponent,
    AdminpageComponent,
    UsersPageComponent,
    DevicesPageComponent,
    MappingsPageComponent,
    UpdateUserComponent,
    CreateUserComponent,
    CreateDeviceComponent,
    UpdateDeviceComponent,
    UpdateMappingComponent,
    ChartComponent,
    ChatComponent,
    ChatClientComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatSnackBarModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatToolbarModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatSnackBarModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTabsModule,
    MatRadioModule,
    MatSelectModule,
    MatTableModule,
    MatIconModule,
    MatDialogModule,
    JwtModule.forRoot({
      config: {
        authScheme: 'Bearer ',
        tokenGetter,
        skipWhenExpired: false,
        allowedDomains: environment.jwt.allowedDomains,
        disallowedRoutes: environment.jwt.disallowedRoutes,
      },
    }),
    LineChartModule
  ],
  providers: [AuthenticationService, DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
