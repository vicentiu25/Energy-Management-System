import { Injectable } from '@angular/core';
import {Device_apiService} from "../api/device_api.service";
import {Observable} from "rxjs";
import {Device} from "../../models/device.model";
import {HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DevicesService {

  constructor(private $device_api: Device_apiService,) { }
  headers = new HttpHeaders({
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });

  getAllDevices():Observable<any>{
    return this.$device_api.get('/device/getAll', {headers: this.headers})
  }
  createDevice(device: Device): void {
    this.$device_api.post('/device/create', {idDevice: device.idDevice, description: device.description, address: device.address, maxConsumption: device.maxConsumption, userId: device.userId}, {headers: this.headers})
  }
  updateDevice(device: Device): void {
    console.log(device)
    this.$device_api.put('/device/update', {idDevice: device.idDevice, description: device.description, address: device.address, maxConsumption: device.maxConsumption, userId: device.userId}, {headers: this.headers})
  }
  deleteDevice(deviceId: number): void {
    this.$device_api.delete('/device/delete?deviceId='+deviceId, {headers: this.headers})
  }
}
