import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {User} from "../../models/user.model";
import {UsersService} from "../users-service/users.service";
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";
import {Device} from "../../models/device.model";
import {DevicesService} from "../devices-service/devices.service";

@Component({
  selector: 'app-update-device',
  templateUrl: './update-device.component.html',
  styleUrls: ['./update-device.component.css']
})
export class UpdateDeviceComponent {
  constructor(
    public dialogRef: MatDialogRef<UpdateDeviceComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Device,
    public devicesService: DevicesService
  ) {}

  updateForm = new FormGroup({
    description: new FormControl(this.data.description, [Validators.required]),
    address: new FormControl(this.data.address, [Validators.required]),
    maxConsumption: new FormControl(this.data.maxConsumption, [Validators.required]),
  })

  onSubmit(formDirective: FormGroupDirective) {
    this.devicesService.updateDevice({
      idDevice: this.data.idDevice,
      description: this.updateForm.get('description')?.value!,
      address: this.updateForm.get('address')?.value!,
      maxConsumption: Number(this.updateForm.get('maxConsumption')?.value!),
      userId: this.data.userId
    })
    this.updateForm.reset()
    formDirective.resetForm()
    this.dialogRef.close();
  }
}
