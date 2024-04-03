import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {User_apiService} from "../api/user_api.service";
import {HttpHeaders, HttpParams} from "@angular/common/http";
import {User} from "../../models/user.model";

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private $user_api: User_apiService,) { }

  getAllUsers():Observable<any>{
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });
    return this.$user_api.get('/user/getAll', {headers: headers})
  }
  createUser(user: User): void {
    this.$user_api.post('/user/register', {idUser: user.idUser, username: user.username, password: user.password, role: user.role}).subscribe()
  }
  updateUser(user: User): void {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });
    this.$user_api.put('/user/update', {idUser: user.idUser, username: user.username, password: user.password, role: user.role}, {headers: headers})
  }
  deleteUser(userId: number): void {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });
    this.$user_api.delete('/user/delete?userId='+userId, {headers: headers})
  }
  checkToken(token: string): void {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });
    this.$user_api.post('/user/token', {headers: headers})
  }
}
