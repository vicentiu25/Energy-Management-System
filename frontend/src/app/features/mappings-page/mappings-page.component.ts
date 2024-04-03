import { Component } from '@angular/core';
import {Observable} from "rxjs";
import {Device} from "../../models/device.model";
import {DevicesService} from "../devices-service/devices.service";
import {MatDialog} from "@angular/material/dialog";
import {AuthenticationService} from "../authentication/authentication.service";
import {Router} from "@angular/router";
import {CreateDeviceComponent} from "../create-device/create-device.component";
import {UpdateMappingComponent} from "../update-mapping/update-mapping.component";

@Component({
  selector: 'app-mappings-page',
  templateUrl: './mappings-page.component.html',
  styleUrls: ['./mappings-page.component.css']
})
export class MappingsPageComponent {
  public dataSource$: Observable<Device[]>
  constructor(
    private devicesService: DevicesService,
    public dialog: MatDialog,
    private authenticationService: AuthenticationService,
    private router: Router) {
    this.dataSource$ = this.devicesService.getAllDevices();
  }

  mapDevice(device: Device): void {
    const dialogRef = this.dialog.open(UpdateMappingComponent, {
      data: device,
      height: '300px',
      width: '400px',
    });

    dialogRef.afterClosed().subscribe(result => {
      setTimeout(() => {
        this.dataSource$ = this.devicesService.getAllDevices();
      }, 100)
    });
  }

  logout(): void{
    this.authenticationService.logout()
    this.router.navigate([''])
  }
}
