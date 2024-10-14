import {CanActivateFn, Router} from "@angular/router";
import {inject} from "@angular/core";

import {AuthService} from "../services/auth.service";

export function IsAuthenticatedGuard(): CanActivateFn {
  return () => {
    const authService:AuthService = inject(AuthService)
    const isAuthenticated: boolean = authService.authenticated.getValue()
    if (isAuthenticated) {
      return true
    } else {
      const router: Router = inject(Router)
      router.navigateByUrl('/auth/login')
      return false
    }
  }
}
