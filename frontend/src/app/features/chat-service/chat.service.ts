import { Injectable } from '@angular/core';
import {Device_apiService} from "../api/device_api.service";
import {Monitoring_apiService} from "../api/monitoring_api.service";
import {DatePipe} from "@angular/common";
import {Observable, Subscription} from "rxjs";
import {Chat_apiService} from "../api/chat_api.service";
import {HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private $chat_api: Chat_apiService) { }
  sendMessageToUser(idUser:number, message:string): Subscription{
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });
    return this.$chat_api.post('/chat/user', {idSender: idUser, message: message}, {headers: headers})
  }
  sendMessageToAdmin(message:string):Subscription{
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });
    return this.$chat_api.post('/chat/admin', message, {headers: headers})
  }
  notifyAdminVisibility() {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });
    return this.$chat_api.post('/chat/admin/seen', null, {headers: headers})
  }
  notifyUsersVisibility(activeUsers: any) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });
    activeUsers.forEach((user: any) => {
      this.$chat_api.post('/chat/user/seen', user.userId, {headers: headers})
    })
  }
  sendTypingToAdmin(status:boolean):Subscription{
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });
    return this.$chat_api.post('/chat/admin/typing', status, {headers: headers})
  }
  sendTypingToUser(status:boolean, userId: number):Subscription{
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });
    return this.$chat_api.post('/chat/user/typing', {status: status, userId: userId}, {headers: headers})
  }
}
