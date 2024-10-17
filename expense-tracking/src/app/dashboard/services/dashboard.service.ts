import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs";
import {IncomeExpenseWidget} from "../models/dashboard.models";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  http: HttpClient = inject(HttpClient)
  serverUrl = environment.serverUrl

  getIncomeExpenseWidget(userId: number): Observable<IncomeExpenseWidget>{
    return this.http.get<IncomeExpenseWidget>(`${this.serverUrl}dashboard/income-expense/${userId}`)
  }

}
