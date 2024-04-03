import { Injectable } from '@angular/core';
import {Device_apiService} from "../api/device_api.service";
import {Observable} from "rxjs";
import {Device} from "../../models/device.model";
import {HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class MappingsService {

  constructor(private $device_api: Device_apiService) { }
  headers = new HttpHeaders({
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  mapDevice(device: Device):void{
    this.$device_api.patch('/device/map/create', device, {headers: this.headers})
  }
}
