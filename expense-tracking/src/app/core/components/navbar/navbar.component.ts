import {Component, inject} from '@angular/core';
import {Router, RouterLink} from "@angular/router";

import {AuthService} from "../../../auth/services/auth.service";
import {UserService} from "../../../users/services/user.service";

import Swal from "sweetalert2";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

  isMenuActive: boolean = false
  isAuthenticated: boolean = false
  currentUserName: string = ''

  authService: AuthService = inject(AuthService)
  userService: UserService = inject(UserService)
  router: Router = inject(Router)

  constructor() {
    this.authService.authenticated.subscribe({
      next: result => this.isAuthenticated = result
    })
    this.userService.currentUserName.subscribe({
      next: result => this.currentUserName = result
    })
  }

  logout(){
    // ask the user for the confirmation
    Swal.fire({
      title: 'Log out',
      text: 'Do you want to logout?',
      icon: 'question',
      showConfirmButton: true,
      showCancelButton: true,
      confirmButtonText: 'Yes, log me out',
      cancelButtonText: 'No, stay logged in'
    }).then(result => {
      if (result.isConfirmed){
        // log out the user
        this.authService.logout()
        this.router.navigateByUrl('/auth/login')
      }
    })
  }

}
