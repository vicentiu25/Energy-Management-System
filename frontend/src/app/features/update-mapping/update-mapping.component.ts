import {Component, Inject, OnInit} from '@angular/core';
import {User} from "../../models/user.model";
import {UsersService} from "../users-service/users.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MappingsService} from "../mappings-service/mappings.service";
import {Device} from "../../models/device.model";

@Component({
  selector: 'app-update-mapping',
  templateUrl: './update-mapping.component.html',
  styleUrls: ['./update-mapping.component.css']
})
export class UpdateMappingComponent implements OnInit{
  users: User[] = [];
  selectedUser: User = {
    idUser: 0,
    username:'',
    password:'',
    role:''
  };
  constructor(
    public dialogRef: MatDialogRef<UpdateMappingComponent>,
    private _snackBar:MatSnackBar,
    private mappingsService: MappingsService,
    @Inject(MAT_DIALOG_DATA) public data: Device,
    private usersService: UsersService) {
  }

  ngOnInit(): void {
    this.fetchUsers();
  }

  fetchUsers(): void {
    this.usersService.getAllUsers().subscribe(
      (users: User[]) => {
        this.users = users;
        this.selectedUser=(this.users)[0]
      }
    );
  }

  mappingForm = new FormGroup({
    userId: new FormControl(0,[Validators.required]),
  })

  onSubmit(formDirective: FormGroupDirective) {
    this.mappingsService.mapDevice({
      idDevice: this.data.idDevice,
      description: '',
      address: '',
      maxConsumption: 0,
      userId: this.selectedUser?.idUser,
    })
    this.mappingForm.reset()
    formDirective.resetForm()
    this.dialogRef.close();
  }

}
