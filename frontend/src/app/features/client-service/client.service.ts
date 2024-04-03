import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Device_apiService} from "../api/device_api.service";
import {Monitoring_apiService} from "../api/monitoring_api.service";
import {DatePipe} from "@angular/common";
import {HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor(private $device_api: Device_apiService, private $monitoring_api: Monitoring_apiService, private datePipe: DatePipe) { }
  headers = new HttpHeaders({
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  });
  getDeviceByUser(userId:number):Observable<any>{
    return this.$device_api.get('/device/getByUser?userId='+userId, {headers: this.headers})
  }
  getAllDevicedataByUser(idUser:number, date:Date):Observable<any>{
    const newDate = this.datePipe.transform(date, 'yyyy-MM-ddTHH:mm:ss') || '';
    return this.$monitoring_api.get('/devicedata/getByDevice?userId=' + idUser + '&date=' +newDate, {headers: this.headers})
  }
}
