import {Component, OnInit} from '@angular/core';
import {interval, Observable, Subscription, switchMap} from "rxjs";
import {User} from "../../models/user.model";
import {Device} from "../../models/device.model";
import {MatDialog} from "@angular/material/dialog";
import {DevicesService} from "../devices-service/devices.service";
import {CreateDeviceComponent} from "../create-device/create-device.component";
import {UpdateDeviceComponent} from "../update-device/update-device.component";
import {AuthenticationService} from "../authentication/authentication.service";
import {Router} from "@angular/router";
import {Devicedata} from "../../models/devicedata.model";
import {ClientService} from "../client-service/client.service";
import * as SockJS from "sockjs-client";
import * as Stomp from "stompjs";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-devices-page',
  templateUrl: './devices-page.component.html',
  styleUrls: ['./devices-page.component.css']
})
export class DevicesPageComponent implements OnInit{
  public dataSource$: Observable<Device[]>
  pollingSubscription!: Subscription;
  deviceData: Devicedata[] = [];
  today: Date = new Date();
  selectedDate: Date = this.today;
  dataSource = this.clientService.getDeviceByUser(Number(localStorage.getItem('idUser')));

  constructor(
    private devicesService: DevicesService,
    public dialog: MatDialog,
    private authenticationService: AuthenticationService,
    private clientService: ClientService,
    private _snackBar: MatSnackBar,
    private router: Router) {
    this.dataSource$ = this.devicesService.getAllDevices();
  }

  webSocket = new SockJS("http://localhost:8083/socket")
  stompClient = Stomp.over(this.webSocket);

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

  newDevice(): void {
    const dialogRef = this.dialog.open(CreateDeviceComponent, {
      height: '400px',
      width: '600px',
    });

    dialogRef.afterClosed().subscribe(result => {
      setTimeout(() => {
        this.dataSource$ = this.devicesService.getAllDevices();
      }, 100)
    });
  }
  editDevice(device: Device): void{
    const dialogRef = this.dialog.open(UpdateDeviceComponent, {
      data: device,
      height: '400px',
      width: '600px',
    });

    dialogRef.afterClosed().subscribe(result => {
      setTimeout(() => {
        this.dataSource$ = this.devicesService.getAllDevices();
      }, 100)
    });

  }
  deleteDevice(deviceId: number): void {
    this.devicesService.deleteDevice(deviceId);
    setTimeout(() => {
      this.dataSource$ = this.devicesService.getAllDevices();
    }, 100)
  }

  logout(): void{
    this.authenticationService.logout()
    this.router.navigate([''])
  }

  onDateChange(event: any) {
    this.selectedDate = event.value;
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
}
