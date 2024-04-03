import {Component, OnInit} from '@angular/core';
import {UsersService} from "../users-service/users.service";
import {Observable} from "rxjs";
import {User} from "../../models/user.model";
import {UpdateUserComponent} from "../update-user/update-user.component";
import {MatDialog} from "@angular/material/dialog";
import {CreateUserComponent} from "../create-user/create-user.component";
import {AuthenticationService} from "../authentication/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-users-page',
  templateUrl: './users-page.component.html',
  styleUrls: ['./users-page.component.css']
})
export class UsersPageComponent {
  public dataSource$: Observable<User[]>
  constructor(private usersService: UsersService,
              public dialog: MatDialog,
              private authenticationService: AuthenticationService,
              private router: Router) {
    this.dataSource$ = this.usersService.getAllUsers();
  }

  newUser(): void {
    const dialogRef = this.dialog.open(CreateUserComponent, {
      height: '400px',
      width: '600px',
    });

    dialogRef.afterClosed().subscribe(result => {
      setTimeout(() => {
        this.dataSource$ = this.usersService.getAllUsers();
      }, 100)
    });
  }
  editUser(user: User): void{
    const dialogRef = this.dialog.open(UpdateUserComponent, {
      data: user,
      height: '400px',
      width: '600px',
    });

    dialogRef.afterClosed().subscribe(result => {
      setTimeout(() => {
        this.dataSource$ = this.usersService.getAllUsers();
      }, 100)
    });
  }
  deleteUser(userId: number): void {
    this.usersService.deleteUser(userId);
    setTimeout(() => {
      this.dataSource$ = this.usersService.getAllUsers();
    }, 100)
  }
  logout(): void{
    this.authenticationService.logout()
    this.router.navigate([''])
  }
}
