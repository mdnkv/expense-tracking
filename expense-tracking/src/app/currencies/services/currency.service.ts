import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs";
import {Currency} from "../models/currencies.models";

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {

  http: HttpClient = inject(HttpClient)
  serverUrl = environment.serverUrl

  getAllCurrenciesForUser (userId: string): Observable<Currency[]>{
    return this.http.get<Currency[]>(`${this.serverUrl}currencies/user/${userId}`)
  }

}
