import {Component, inject} from '@angular/core';
import {RouterLink} from "@angular/router";

import {AuthService} from "../../../auth/services/auth.service";

@Component({
    selector: 'app-not-found-view',
    imports: [RouterLink],
    templateUrl: './not-found-view.component.html',
    styleUrl: './not-found-view.component.css'
})
export class NotFoundViewComponent {

  isAuthenticated: boolean = false

  authService: AuthService = inject(AuthService)

  constructor() {
     this.isAuthenticated = this.authService.authenticated.getValue()
  }

}
