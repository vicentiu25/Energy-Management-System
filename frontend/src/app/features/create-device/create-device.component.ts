import { Component } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {UsersService} from "../users-service/users.service";
import {DevicesService} from "../devices-service/devices.service";
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";

@Component({
  selector: 'app-create-device',
  templateUrl: './create-device.component.html',
  styleUrls: ['./create-device.component.css']
})
export class CreateDeviceComponent {
  constructor(
    public dialogRef: MatDialogRef<CreateDeviceComponent>,
    public devicesService: DevicesService
  ) {}

  createForm = new FormGroup({
    description: new FormControl('', [Validators.required]),
    address: new FormControl('', [Validators.required]),
    maxConsumption: new FormControl('', [Validators.required]),
  })

  onSubmit(formDirective: FormGroupDirective) {
    this.devicesService.createDevice({
      idDevice: 0,
      description: this.createForm.get('description')?.value!,
      address: this.createForm.get('address')?.value!,
      maxConsumption: Number(this.createForm.get('maxConsumption')?.value!),
      userId: 0
    })
    this.createForm.reset()
    formDirective.resetForm()
    this.dialogRef.close();
  }
}
