import {Component, OnInit} from '@angular/core';
import {ClientService} from "../client-service/client.service";
import {AuthenticationService} from "../authentication/authentication.service";
import {Router} from "@angular/router";
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {Device} from "../../models/device.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {interval, Subscription, switchMap} from "rxjs";
import {Devicedata} from "../../models/devicedata.model";

@Component({
  selector: 'app-client-page',
  templateUrl: './client-page.component.html',
  styleUrls: ['./client-page.component.css']
})
export class ClientPageComponent implements OnInit{
  constructor(
    private clientService: ClientService,
    private authenticationService: AuthenticationService,
    private router: Router,
    private _snackBar: MatSnackBar) {
  }
  today: Date = new Date();
  pollingSubscription!: Subscription;
  deviceData: Devicedata[] = [];
  selectedDate: Date = this.today;

  webSocket = new SockJS("http://localhost:8083/socket")
  stompClient = Stomp.over(this.webSocket);

  displayedColumns: string[] = ['Device', 'description', 'address', 'maxConsumption', 'userId'];
  dataSource = this.clientService.getDeviceByUser(Number(localStorage.getItem('idUser')));

  logout(): void{
    this.authenticationService.logout()
    this.router.navigate([''])
  }

  ngOnInit(): void {
    this.today.setHours(0, 0, 0, 0);
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe("/queue/socket/data", message => {
        this.checkMessage(message.body)
      })
    })
    this.startPolling();
  }

  startPolling(): void {
    const intervalTime = 5000;
    this.pollingSubscription = interval(intervalTime).pipe(
      switchMap(() => this.clientService.getAllDevicedataByUser(Number(localStorage.getItem('idUser')), this.selectedDate))
    ).subscribe((data: Devicedata[]) => {
      this.deviceData = data;
    });
  }

  checkMessage(message: string): void {
    this.dataSource.subscribe(devices => {
      const deviceIds = devices.map((device: Device) => device.idDevice);
      if (deviceIds.includes(Number(message))) {
        this._snackBar.open("Device " + Number(message) + " exceeded the hourly maximum consumption.", 'OK', {
          duration: 2000,
          panelClass: 'success-snackbar'
        })
      }
    });
  }

  onDateChange(event: any) {
    this.selectedDate = event.value;
  }

  chat(): void {
    this.router.navigate(["/client/chat"]);
  }
}
