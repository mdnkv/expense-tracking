import {Component, inject, OnInit} from '@angular/core';

import {Category} from "../../models/categories.models";
import {CategoryService} from "../../services/category.service";
import {HttpErrorResponse} from "@angular/common/http";
import {AddCategoryComponent} from "../../components/add-category/add-category.component";
import {
  CategoriesSortDropdownComponent
} from "../../components/categories-sort-dropdown/categories-sort-dropdown.component";
import {CategoriesListComponent} from "../../components/categories-list/categories-list.component";
import Swal from "sweetalert2";
import {ErrorPlaceholderComponent} from "../../../core/components/error-placeholder/error-placeholder.component";

@Component({
    selector: 'app-categories-view',
    imports: [
        AddCategoryComponent,
        CategoriesSortDropdownComponent,
        CategoriesListComponent,
        ErrorPlaceholderComponent
    ],
    templateUrl: './categories-view.component.html',
    styleUrl: './categories-view.component.css'
})
export class CategoriesViewComponent implements OnInit {

  isLoadingError: boolean = false
  categoriesList: Category[] = []
  categoryService: CategoryService = inject(CategoryService)

  ngOnInit() {
    const userId = localStorage.getItem("UserId") as string

    this.categoryService.getAllCategoriesForUser(userId).subscribe({
      next: result =>{
        this.categoriesList = result
        this.isLoadingError = false
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)
        this.isLoadingError = true
      }
    })

  }

  createCategory(category: Category){
    this.categoryService.createCategory(category).subscribe({
      next: result => {
        // add to the categories list
        this.categoriesList.push(result)
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)
        // show error message
        Swal.fire({
          icon: 'error',
          text: 'Something went wrong. Please try again later'
        })
      }
    })
  }

  deleteCategory(id: string){
    this.categoryService.deleteCategory(id).subscribe({
      next: result => {
        this.categoriesList = this.categoriesList.filter(e => e.id != id)
      },
      error: (err: HttpErrorResponse) =>{
        console.log(err)
        // show error message
        Swal.fire({
          icon: 'error',
          text: 'Something went wrong. Please try again later'
        })
      }
    })
  }

  sortCategories(order: string){
    if (order == 'name-asc'){
      this.categoriesList.sort((c1, c2) => {
        return c1.name.localeCompare(c2.name)
      })
    } else {
      this.categoriesList.sort((c1, c2) => {
        return c2.name.localeCompare(c1.name)
      })
    }
  }

}
