import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {User} from "../../models/user.model";
import {UsersService} from "../users-service/users.service";
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent {
  constructor(
    public dialogRef: MatDialogRef<CreateUserComponent>,
    public userService: UsersService
  ) {}

  createForm = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.minLength(6)]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)]),
    role: new FormControl('', [Validators.required]),
  })

  onSubmit(formDirective: FormGroupDirective) {
    this.userService.createUser({
      idUser: 0,
      username: this.createForm.get('username')?.value!,
      password: this.createForm.get('password')?.value!,
      role: this.createForm.get('role')?.value!
    })
    this.createForm.reset()
    formDirective.resetForm()
    this.dialogRef.close();
  }
}
