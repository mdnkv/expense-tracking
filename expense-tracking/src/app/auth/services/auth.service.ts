import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {LoginRequest, LoginResponse} from "../models/auth.models";
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authenticated: BehaviorSubject<boolean> = new BehaviorSubject(false)
  http: HttpClient = inject(HttpClient)
  serverUrl = environment.serverUrl

  constructor() {
    const isAuthenticated = localStorage.getItem('token') != null
    this.authenticated.next(isAuthenticated)
  }

  login (body: LoginRequest): Observable<LoginResponse>{
    return this.http.post<LoginResponse>(`${this.serverUrl}auth/login`, body)
  }

  logout(){
    localStorage.clear()
    this.authenticated.next(false)
  }

}
