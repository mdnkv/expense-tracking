import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {ChangePasswordRequest, SignupRequest, SignupResponse, User} from "../models/users.models";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  http: HttpClient = inject(HttpClient)
  serverUrl = environment.serverUrl

  signup(body: SignupRequest): Observable<SignupResponse> {
    return this.http.post<SignupResponse>(`${this.serverUrl}users/create`, body)
  }

  getUserById (id: number): Observable<User> {
    return this.http.get<User>(`${this.serverUrl}users/user/${id}`)
  }

  updateUser(body: User): Observable<User>{
    return this.http.put<User>(`${this.serverUrl}users/update/user`, body)
  }

  updatePassword(body: ChangePasswordRequest): Observable<void>{
    return this.http.post<void>(`${this.serverUrl}users/update/password`, body)
  }


}
