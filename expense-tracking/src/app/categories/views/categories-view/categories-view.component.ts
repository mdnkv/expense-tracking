import {Component, inject, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Category} from "../../models/categories.models";
import {CategoryService} from "../../services/category.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-categories-view',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './categories-view.component.html',
  styleUrl: './categories-view.component.css'
})
export class CategoriesViewComponent implements OnInit {

  isFormLoading: boolean = false
  isShowModal: boolean = false
  isError: boolean = false
  categoriesList: Category[] = []

  formBuilder: FormBuilder = inject(FormBuilder)
  categoryCreateForm: FormGroup = this.formBuilder.group({
    name: ['', [Validators.required]]
  })

  categoryService: CategoryService = inject(CategoryService)

  ngOnInit() {
    const userId = localStorage.getItem("userId") as string
    const id = Number.parseInt(userId)

    this.categoryService.getAllCategoriesForUser(id).subscribe({
      next: result =>{
        this.categoriesList = result
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)
      }
    })

  }

  onFormSubmit(){
    this.isFormLoading = true
    this.isError = false

    const userId = localStorage.getItem("userId") as string
    const id = Number.parseInt(userId)

    const payload: Category = {
      name: this.categoryCreateForm.get('name')?.value,
      userId: id
    }

    this.categoryService.createCategory(payload).subscribe({
      next: result => {
        // add to the categories list
        this.categoriesList.push(result)

        // reset form
        this.categoryCreateForm.reset()
        this.isShowModal = false
        this.isFormLoading = false
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)

        this.isError = true
        this.isFormLoading = false
      }
    })


  }

  onCloseFormModal(){
    this.categoryCreateForm.reset()
    this.isShowModal = false
  }

  deleteCategory(id: number){
    this.categoryService.deleteCategory(id).subscribe({
      next: result => {
        this.categoriesList = this.categoriesList.filter(e => e.id != id)
      },
      error: (err: HttpErrorResponse) =>{
        console.log(err)
      }
    })
  }

}
