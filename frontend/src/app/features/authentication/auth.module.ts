import { NgModule } from "@angular/core";
import { JwtModule } from "@auth0/angular-jwt";
import {environment} from "../../environment/environment";
import {AuthenticationService} from "./authentication.service";

export function tokenGetter() {
  return localStorage.getItem(environment.jwt.tokenKey);
}

@NgModule({
  providers: [AuthenticationService],
  imports: [
    JwtModule.forRoot({
      config: {
        authScheme: 'Bearer ',
        tokenGetter,
        skipWhenExpired: false,
        allowedDomains: environment.jwt.allowedDomains,
        disallowedRoutes: environment.jwt.disallowedRoutes,
      },
    })
  ],
  exports: []
})
export class AuthModule {

}
