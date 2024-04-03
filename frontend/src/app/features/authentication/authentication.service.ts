import {Injectable} from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";
import {environment} from "../../environment/environment";
import {map, Subscription} from "rxjs";
import {User_apiService} from "../api/user_api.service";
import {User} from "../../models/user.model";
import {HttpHeaders} from "@angular/common/http";


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  public token: string | null = null;
  public user: User | null = null;

  constructor(
    private $user_api: User_apiService,
    private $jwtHelper: JwtHelperService,
  ) {
    this.loadToken();
  }

  loadToken() {
    const token = window.localStorage.getItem(environment.jwt.tokenKey);
    if (token) {
      this.validateToken(token);
    }
  }

  isLoggedIn() {
    return this.user && this.token;
  }

  login(user: User){
    return this.$user_api.post('/user/login', user).pipe(
      map( (resp) => {
        if ( !resp.token || !this.validateToken(resp.token) ) throw new Error(resp);
        window.localStorage.setItem(environment.jwt.tokenKey, resp.token);
        window.localStorage.setItem('idUser', String(this.$jwtHelper.decodeToken(resp.token).sub));
      } )
    );
  }

  logout(){
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });
    this.user = null;
    this.token = null;
    window.localStorage.removeItem(environment.jwt.tokenKey);
    window.localStorage.removeItem('idUser');
    this.$user_api.post('/user/logout', null, {headers: headers}).subscribe()
  }

  register(user: User){
    return this.$user_api.post('/user/register', user).pipe(
      map(
        (resp) => {
          if( !resp.username ) throw new Error(resp);
        }
      )
    )
  }

  validateToken(token: string){
    try{
      if( this.$jwtHelper.isTokenExpired(token) ){
        this.logout();
      }
      this.user = {
        idUser: this.$jwtHelper.decodeToken(token).sub,
        username: '',
        password: '',
        role: this.$jwtHelper.decodeToken(token).aud
      };
      this.token = token;
      return true;
    } catch (e) {
      console.log(e);
      window.localStorage.removeItem(environment.jwt.tokenKey)
      this.token = null;
      return false;
    }
  }
}
