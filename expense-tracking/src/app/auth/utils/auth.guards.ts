import {CanActivateFn, Router} from "@angular/router";
import {inject} from "@angular/core";

export function IsAuthenticatedGuard(): CanActivateFn {
  return () => {
    const token = localStorage.getItem('AuthenticationToken') as string
    const isAuthenticated: boolean = token != null
    const router: Router = inject(Router)
    if (isAuthenticated) {
      // verify that the token is not expired
      const expirationValue = localStorage.getItem('TokenExpiration') as string
      // if expiration value is null, go to login page
      if (expirationValue == null){
        localStorage.clear() // clear localstorage
        router.navigateByUrl('/auth/login')
        return false
      }
      const expirationDateTime = new Date(Number.parseInt(expirationValue))
      const currentDateTime = new Date()
      if (expirationDateTime < currentDateTime){
        // token us expired
        localStorage.clear() // clear localstorage
        router.navigateByUrl('/auth/login')
        return false
      }
      // proceed to the route
      return true
    } else {
      localStorage.clear() // clear localstorage
      router.navigateByUrl('/auth/login')
      return false
    }
  }
}
