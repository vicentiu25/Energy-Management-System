import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {ClientService} from "../client-service/client.service";
import {Chat_apiService} from "../api/chat_api.service";
import {ChatService} from "../chat-service/chat.service";
import {Message} from "../../models/message.model";
import {UsersService} from "../users-service/users.service";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  activeUsers: { userId: number, messages: Message[], messageToSend: string, isTyping: boolean }[] = [];

  webSocket = new SockJS("http://localhost:8084/socket")
  stompClient = Stomp.over(this.webSocket);

  constructor(
    private chatService: ChatService,
    private userService: UsersService
  ){}

  ngOnInit(): void {
    this.stompClient.connect({}, () => {
      const adminId = Number(localStorage.getItem('idUser'));
      this.stompClient.subscribe(`/user/${adminId}/chat`, message => {
        if (message.command === 'CONNECTED') {
          const sessionId = (message.headers as { [key: string]: string })['session'];
          this.userService.checkToken(sessionId)
        }
        const messageData = JSON.parse(message.body);
        const userId = messageData.idSender;
        const userIndex = this.activeUsers.findIndex(u => u.userId === userId);
        if (messageData.message == "") {
          if (messageData.seen)  {
            this.activeUsers[userIndex].messages.forEach((message) => {
              if(message.emittedMe) message.seen = true;
            });
          } else {
            this.activeUsers[userIndex].isTyping = messageData.typing;
          }
        } else {
            if (!document.hidden) {
              this.chatService.notifyUsersVisibility(this.activeUsers);
            }
            if (userIndex !== -1) {
              this.activeUsers[userIndex].messages.push({seen: false, content: 'User ' + userId + ': ' + messageData.message, emittedMe: false});
            } else {
              this.activeUsers.push({
                userId: userId,
                messages: [{seen: false, content: 'User ' + userId + ': ' + messageData.message, emittedMe: false}],
                messageToSend: '',
                isTyping: false
              });
            }
        }
      })
    })
    document.addEventListener("visibilitychange", () => {
      if (!document.hidden) {
        this.chatService.notifyUsersVisibility(this.activeUsers);
      }
    })
  }

  sendMessage(userId: number) {
    const userIndex = this.activeUsers.findIndex(u => u.userId === userId);
    if (userIndex !== -1 && this.activeUsers[userIndex].messageToSend.trim() !== '') {
      this.chatService.sendMessageToUser(userId, this.activeUsers[userIndex].messageToSend);
      this.chatService.sendTypingToUser(false, userId);
      this.activeUsers[userIndex].messages.push({seen: false, content:'Admin: ' + this.activeUsers[userIndex].messageToSend, emittedMe: true});
      this.activeUsers[userIndex].messageToSend = '';
    }
  }

  onMessageChange(userId: number): void {
    const userIndex = this.activeUsers.findIndex(u => u.userId === userId);
    if (this.activeUsers[userIndex].messageToSend.trim().length > 0) {
      this.chatService.sendTypingToUser(true, userId);
    } else {
      this.chatService.sendTypingToUser(false, userId);
    }
  }
}
