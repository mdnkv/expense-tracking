import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Account} from "../models/accounts.models";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  http: HttpClient = inject(HttpClient)
  serverUrl = environment.serverUrl

  createAccount(body: Account): Observable<Account>{
    return this.http.post<Account>(`${this.serverUrl}accounts/create`, body)
  }

  updateAccount(body: Account): Observable<Account>{
    return this.http.put<Account>(`${this.serverUrl}accounts/update`, body)
  }

  deleteAccount(id: string): Observable<void>{
    return this.http.delete<void>(`${this.serverUrl}accounts/delete/${id}`)
  }

  getAllAccountsForUser(userId: string): Observable<Account[]>{
    return this.http.get<Account[]>(`${this.serverUrl}accounts/user/${userId}`)
  }

  getAccountById(id: string): Observable<Account>{
    return this.http.get<Account>(`${this.serverUrl}accounts/account/${id}`)
  }

}
