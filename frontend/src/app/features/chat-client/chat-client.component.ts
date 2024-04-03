import { Component } from '@angular/core';
import {ChatService} from "../chat-service/chat.service";
import * as SockJS from "sockjs-client";
import * as Stomp from "stompjs";
import {Message} from "../../models/message.model";
import {UsersService} from "../users-service/users.service";

@Component({
  selector: 'app-chat-client',
  templateUrl: './chat-client.component.html',
  styleUrls: ['./chat-client.component.css']
})
export class ChatClientComponent {
  messageToSend: string = '';
  messages: Message[] = [];
  subscription: any;
  isTyping: boolean = false;

  webSocket = new SockJS("http://localhost:8084/socket")
  stompClient = Stomp.over(this.webSocket);

  constructor(
    private chatService: ChatService,
    private userService: UsersService
  ){}

  ngOnInit(): void {
    this.stompClient.connect({}, () => {
      const userId = Number(localStorage.getItem('idUser'));
      this.stompClient.subscribe(`/user/${userId}/chat`, (message) => {
        if (message.command === 'CONNECTED') {
          const sessionId = (message.headers as { [key: string]: string })['session'];
          this.userService.checkToken(sessionId)
        }
        const messageData = JSON.parse(message.body);
        if (messageData.message == "") {
          if (messageData.seen)  {
            this.messages.forEach((message) => {
              if(message.emittedMe) message.seen = true;
            });
          } else {
            this.isTyping = messageData.typing;
          }
        } else {
          if (!document.hidden) {
            this.chatService.notifyAdminVisibility();
          }
          this.messages.push({seen: false, content: 'Admin : ' + messageData.message, emittedMe: false});
        }
      })
    })
    document.addEventListener("visibilitychange", () => {
      if (!document.hidden) {
        this.chatService.notifyAdminVisibility();
      }
    })
  }

  sendMessage() {
    if (this.messageToSend.trim() !== '') {
      this.messages.push({seen: false, content: `User ${localStorage.getItem('idUser')}: ${this.messageToSend}`, emittedMe: true});
      this.chatService.sendMessageToAdmin(this.messageToSend);
      this.chatService.sendTypingToAdmin(false);
      this.messageToSend = '';
    }
  }

  onMessageChange(): void {
    if (this.messageToSend.trim().length > 0) {
      this.chatService.sendTypingToAdmin(true);
    } else {
      this.chatService.sendTypingToAdmin(false);
    }
  }
}
