import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {User} from "../../models/user.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit{
  @Output() submitForm = new EventEmitter<User>()
  color: string = 'red';

  constructor(private _snackBar:MatSnackBar) {
  }

  ngOnInit(): void {
  }

  registerForm = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.minLength(6)]),
    password: new FormControl('', [Validators.required, Validators.minLength(8)]),
  })

  onSubmit(formDirective: FormGroupDirective) {
    this.submitForm.emit(formDirective.value)
    this.registerForm.reset()
    formDirective.resetForm()
  }
}
