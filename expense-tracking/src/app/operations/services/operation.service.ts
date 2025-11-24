import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

import {environment} from "../../../environments/environment";
import {Operation} from "../models/operations.models";

@Injectable({
  providedIn: 'root'
})
export class OperationService {

  http: HttpClient = inject(HttpClient)
  serverUrl = environment.serverUrl

  createOperation(body: Operation): Observable<Operation>{
    return this.http.post<Operation>(`${this.serverUrl}operations/create`, body)
  }

  updateOperation(body: Operation): Observable<Operation>{
    return this.http.put<Operation>(`${this.serverUrl}operations/update`, body)
  }


  deleteOperation (id: string): Observable<void>{
    return this.http.delete<void>(`${this.serverUrl}operations/delete/${id}`)
  }

  getAllOperationsForUser(userId: string): Observable<Operation[]>{
    return this.http.get<Operation[]>(`${this.serverUrl}operations/user/${userId}`)
  }

  getOperationById(id: string): Observable<Operation>{
    return this.http.get<Operation>(`${this.serverUrl}operations/operation/${id}`)
  }


}
