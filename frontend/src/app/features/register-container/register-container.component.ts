import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {User} from "../../models/user.model";
import {AuthenticationService} from "../authentication/authentication.service";

@Component({
  selector: 'app-register-container',
  templateUrl: './register-container.component.html',
  styleUrls: ['./register-container.component.css']
})
export class RegisterContainerComponent implements OnInit{

  constructor(private authenticationService: AuthenticationService, private router: Router, private _snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
  }

  registerUser(user: User) {
    user.role = 'CLIENT'
    this.authenticationService.register(user).subscribe((result: any) => {
      this._snackBar.open("Account created", 'OK', {
        duration: 10000,
        panelClass: 'success-snackbar'
      })
      this.router.navigate(["/"]);
    }, (e: { error: string; }) => {
      this._snackBar.open(e.error, 'OK', {
        duration: 10000,
        panelClass: 'fail-snackbar'
      })
      console.log(e);
    })
  };
}
