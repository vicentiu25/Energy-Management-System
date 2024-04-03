import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {AuthenticationService} from "../authentication/authentication.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ClientPageGuard implements CanActivate {
  constructor(
    private $auth: AuthenticationService,
    private $router: Router
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if( !this.$auth.isLoggedIn()){
      window.sessionStorage.setItem('target_route', state.url);

      this.$router.navigate(['/login']);

      return false;
    }

    if(this.$auth.user?.role == 'ADMINISTRATOR') {
      this.$router.navigate(['/admin']);

      return false;
    }
    return true;
  }
}
