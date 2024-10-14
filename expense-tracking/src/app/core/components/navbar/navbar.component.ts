import {Component, inject} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {AuthService} from "../../../auth/services/auth.service";

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

  authService: AuthService = inject(AuthService)
  router: Router = inject(Router)

  constructor() {
    this.authService.authenticated.subscribe({
      next: result => this.isAuthenticated = result
    })
  }

  logout(){
    this.authService.logout()
    this.router.navigateByUrl('/auth/login')
  }

}
