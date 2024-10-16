import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {OperationRequest, OperationResponse} from "../models/operations.models";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class OperationService {

  http: HttpClient = inject(HttpClient)
  serverUrl = environment.serverUrl

  createOperation(body: OperationRequest): Observable<OperationResponse>{
    return this.http.post<OperationResponse>(`${this.serverUrl}operations/create`, body)
  }

  updateOperation(body: OperationRequest): Observable<OperationResponse>{
    return this.http.put<OperationResponse>(`${this.serverUrl}operations/update`, body)
  }


  deleteOperation (id: number): Observable<void>{
    return this.http.delete<void>(`${this.serverUrl}operations/delete/${id}`)
  }

  getAllOperationsForUser(userId: number): Observable<OperationResponse[]>{
    return this.http.get<OperationResponse[]>(`${this.serverUrl}operations/user/${userId}`)
  }

  getOperationById(id: number): Observable<OperationResponse>{
    return this.http.get<OperationResponse>(`${this.serverUrl}operations/operation/${id}`)
  }


}
