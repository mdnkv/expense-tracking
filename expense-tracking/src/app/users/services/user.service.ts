import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, map, Observable} from "rxjs";

import {environment} from "../../../environments/environment";
import {ChangePasswordRequest, SignupRequest, SignupResponse, User} from "../models/users.models";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  http: HttpClient = inject(HttpClient)
  serverUrl = environment.serverUrl

  currentUserName: BehaviorSubject<string> = new BehaviorSubject('')

  constructor() {
    const savedName = localStorage.getItem('currentUserName') as string
    this.currentUserName.next(savedName)
  }


  signup(body: SignupRequest): Observable<SignupResponse> {
    return this.http.post<SignupResponse>(`${this.serverUrl}users/create`, body)
  }

  getUserById (id: string): Observable<User> {
    return this.http.get<User>(`${this.serverUrl}users/user/${id}`).pipe(
      map(result => {
        const name = `${result.firstName} ${result.lastName.substring(0,1)}`
        localStorage.setItem('currentUserName', name)
        this.currentUserName.next(name)
        return result
      })
    )
  }

  updateUser(body: User): Observable<User>{
    return this.http.put<User>(`${this.serverUrl}users/update/user`, body).pipe(
      map(result => {
        const name = `${result.firstName} ${result.lastName.substring(0,1)}`
        localStorage.setItem('currentUserName', name)
        this.currentUserName.next(name)
        return result
      })
    )
  }

  updatePassword(body: ChangePasswordRequest): Observable<void>{
    return this.http.post<void>(`${this.serverUrl}users/update/password`, body)
  }


}
