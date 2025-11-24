import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, map, Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {LoginRequest, LoginResponse} from "../models/auth.models";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authenticated: BehaviorSubject<boolean> = new BehaviorSubject(false)
  http: HttpClient = inject(HttpClient)
  serverUrl = environment.serverUrl

  constructor() {
    const isAuthenticated = localStorage.getItem('AuthenticationToken') != null
    this.authenticated.next(isAuthenticated)
  }

  login (body: LoginRequest): Observable<LoginResponse>{
    return this.http.post<LoginResponse>(`${this.serverUrl}auth/login`, body).pipe(
      map(result => {
        // save authentication data
        localStorage.setItem('AuthenticationToken', result.token)
        localStorage.setItem('UserId', result.id)

        // calculate expiration datetime
        const currentDateTime = new Date()
        const expirationDateTime = currentDateTime.setTime(currentDateTime.getTime() + 8640000)
        localStorage.setItem("TokenExpiration", expirationDateTime.toString())

        this.authenticated.next(true)
        return result
      })
    )
  }


  logoutUserLocally() {
    localStorage.clear()
    this.authenticated.next(false)
  }

  logout(): Observable<void>{
    return this.http.post<void>(`${this.serverUrl}auth/logout`, {}).pipe(
      map(result => {
        this.logoutUserLocally()
        return result
      })
    )
  }

}
