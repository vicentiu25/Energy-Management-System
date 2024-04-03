import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";
import {User} from "../../models/user.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  @Output() submitForm = new EventEmitter<User>()
  color: string = 'red';

  constructor(private _snackBar:MatSnackBar, private router: Router) {
  }

  ngOnInit(): void {
  }

  loginForm = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.minLength(6)]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)]),
  })

  onSubmit(formDirective: FormGroupDirective) {
    this.submitForm.emit(formDirective.value)
    this.loginForm.reset()
    formDirective.resetForm()
  }

  register(): void {
    this.router.navigate(["/register"]);
  }
}
