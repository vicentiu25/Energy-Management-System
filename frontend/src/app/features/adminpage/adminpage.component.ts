import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../authentication/authentication.service";

@Component({
  selector: 'app-adminpage',
  templateUrl: './adminpage.component.html',
  styleUrls: ['./adminpage.component.css']
})
export class AdminpageComponent {
  constructor(private authenticationService: AuthenticationService,private router: Router) {
  }

  users(): void {
    this.router.navigate(["/admin/users"]);
  }

  devices(): void {
    this.router.navigate(["/admin/devices"]);
  }

  mappings(): void {
    this.router.navigate(["/admin/mappings"]);
  }

  chat(): void {
    this.router.navigate(["/admin/chat"]);
  }

  logout(): void{
    this.authenticationService.logout()
    this.router.navigate([''])
  }
}
