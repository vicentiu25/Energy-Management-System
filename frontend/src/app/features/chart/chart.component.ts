import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {interval, Observable, Subscription, switchMap} from "rxjs";
import {Device} from "../../models/device.model";
import {Devicedata} from "../../models/devicedata.model";
import {DevicesService} from "../devices-service/devices.service";
import {ClientService} from "../client-service/client.service";

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnChanges{
  @Input()
  deviceData: Devicedata[] = [];

  chartData: Devicedata[] = [];

  ngOnChanges(): void {
    const groupedData = this.groupDataByDeviceId(this.deviceData);
    this.chartData = this.transformDataForChart(groupedData);
  }

  groupDataByDeviceId(data: Devicedata[]): { [key: string]: Devicedata[] } {
    return data.reduce((acc: { [key: string]: Devicedata[] }, curr: Devicedata) => {
      if (!acc[curr.idDevice]) {
        acc[curr.idDevice] = [];
      }
      acc[curr.idDevice].push(curr);
      return acc;
    }, {});
  }

  transformDataForChart(groupedData: { [key: string]: Devicedata[] }): any[] {
    const chartData = [];
    for (const deviceId in groupedData) {
      if (groupedData.hasOwnProperty(deviceId)) {
        const deviceValues = groupedData[deviceId];
        const deviceLine = {
          name: `Device ${deviceId}`,
          series: deviceValues.map(device => ({
            name: new Date(device.timestamp),
            value: device.consumption
          }))
        };
        chartData.push(deviceLine);
      }
    }
    return chartData;
  }

}
