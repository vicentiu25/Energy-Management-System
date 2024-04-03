import {Component, OnInit} from '@angular/core';
import {User} from "../../models/user.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";
import {AuthenticationService} from "../authentication/authentication.service";
@Component({
  selector: 'app-login-container',
  templateUrl: './login-container.component.html',
  styleUrls: ['./login-container.component.css']
})

export class LoginContainerComponent implements OnInit {

  constructor(private authenticationService: AuthenticationService, private router: Router, private _snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
  }

  loginUser(user: User) {
    this.authenticationService.login(user).subscribe((result: any) => {
      this._snackBar.open("Hello! You were successfully logged in!", 'OK', {
        duration: 10000,
        panelClass: 'success-snackbar'
      })
      if(this.authenticationService.user?.role == 'CLIENT') this.router.navigate(["/client"]);
      else this.router.navigate(["/admin"]);
    }, (e: { error: string; }) => {
      this._snackBar.open(e.error, 'OK', {
        duration: 10000,
        panelClass: 'fail-snackbar'
      })
    })
  };
}
