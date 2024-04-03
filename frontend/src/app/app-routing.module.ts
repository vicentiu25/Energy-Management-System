import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginContainerComponent} from "./features/login-container/login-container.component";
import {RegisterContainerComponent} from "./features/register-container/register-container.component";
import {ClientPageComponent} from "./features/client-page/client-page.component";
import {ClientPageGuard} from "./features/client-page/client-page.guard";
import {AdminpageComponent} from "./features/adminpage/adminpage.component";
import {AdminPageGuard} from "./features/adminpage/adminpage.guard";
import {UsersPageComponent} from "./features/users-page/users-page.component";
import {DevicesPageComponent} from "./features/devices-page/devices-page.component";
import {MappingsPageComponent} from "./features/mappings-page/mappings-page.component";
import {ChatComponent} from "./features/chat/chat.component";
import {ChatClientComponent} from "./features/chat-client/chat-client.component";

const routes: Routes = [
  {
    path:'',
    component:LoginContainerComponent
  },
  {
    path:'register',
    component:RegisterContainerComponent
  },
  {
    path:'client',
    canActivate: [ClientPageGuard],
    component:ClientPageComponent
  },
  {
    path: 'admin',
    canActivate: [AdminPageGuard],
    component: AdminpageComponent
  },
  {
    path:'admin/users',
    canActivate: [AdminPageGuard],
    component:UsersPageComponent,
  },
  {
    path:'admin/devices',
    canActivate: [AdminPageGuard],
    component:DevicesPageComponent,
  },
  {
    path:'admin/mappings',
    canActivate: [AdminPageGuard],
    component:MappingsPageComponent,
  },
  {
    path:'admin/chat',
    canActivate: [AdminPageGuard],
    component:ChatComponent,
  },
  {
    path:'client/chat',
    canActivate: [ClientPageGuard],
    component:ChatClientComponent,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
