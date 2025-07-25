import {HttpHandlerFn, HttpRequest} from "@angular/common/http";

export function tokenInterceptor(request: HttpRequest<unknown>, next: HttpHandlerFn){
  const token = localStorage.getItem('AuthenticationToken')
  if (token != null) {
    const authenticatedRequest = request.clone({
      headers: request.headers.append('Authorization', `Token ${token}`)
    })
    return next(authenticatedRequest)
  } else {
    return next (request)
  }
}
