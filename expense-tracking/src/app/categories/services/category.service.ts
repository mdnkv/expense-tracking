import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Category} from "../models/categories.models";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  http: HttpClient = inject(HttpClient)
  serverUrl = environment.serverUrl

  createCategory(body: Category): Observable<Category>{
    return this.http.post<Category>(`${this.serverUrl}categories/create`, body)
  }

  updateCategory(body: Category): Observable<Category>{
    return this.http.put<Category>(`${this.serverUrl}categories/update`, body)
  }

  getAllCategoriesForUser(userId: number): Observable<Category[]>{
    return this.http.get<Category[]>(`${this.serverUrl}categories/user/${userId}`)
  }

  deleteCategory(id: number): Observable<void>{
    return this.http.delete<void>(`${this.serverUrl}categories/delete/${id}`)
  }

  getCategoryById(id: number): Observable<Category> {
    return this.http.get<Category>(`${this.serverUrl}categories/category/${id}`)
  }


}
