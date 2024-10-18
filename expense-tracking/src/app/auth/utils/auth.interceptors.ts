import {HttpHandlerFn, HttpRequest} from "@angular/common/http";

export function tokenInterceptor(request: HttpRequest<unknown>, next: HttpHandlerFn){
  const token = localStorage.getItem('token')
  if (token != null) {
    const authenticatedRequest = request.clone({
      headers: request.headers.append('Authorization', `Bearer ${token}`)
    })
    return next(authenticatedRequest)
  } else {
    return next (request)
  }
}
