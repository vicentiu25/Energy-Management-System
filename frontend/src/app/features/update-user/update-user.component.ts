import {Component, Inject} from '@angular/core';
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {User} from "../../models/user.model";
import {UsersService} from "../users-service/users.service";

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css'],
})
export class UpdateUserComponent {
  constructor(
    public dialogRef: MatDialogRef<UpdateUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: User,
    public userService: UsersService
  ) {}

  updateForm = new FormGroup({
    username: new FormControl(this.data.username, [Validators.required, Validators.minLength(6)]),
    role: new FormControl(this.data.role, [Validators.required]),
  })

  onSubmit(formDirective: FormGroupDirective) {
    this.userService.updateUser({
      idUser: this.data.idUser,
      username: this.updateForm.get('username')?.value!,
      password: '',
      role: this.updateForm.get('role')?.value!
    })
    this.updateForm.reset()
    formDirective.resetForm()
    this.dialogRef.close();
  }
}
