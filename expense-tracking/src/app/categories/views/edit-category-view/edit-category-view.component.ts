import {Component, inject, input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {CategoryService} from "../../services/category.service";
import {Category} from "../../models/categories.models";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
    selector: 'app-edit-category-view',
    imports: [ReactiveFormsModule],
    templateUrl: './edit-category-view.component.html',
    styleUrl: './edit-category-view.component.css'
})
export class EditCategoryViewComponent implements OnInit{

  id = input.required<string>()

  isSuccess: boolean = false
  isError: boolean = false
  isFormLoading: boolean = false


  router: Router = inject(Router)
  categoryService: CategoryService = inject(CategoryService)
  formBuilder: FormBuilder = inject(FormBuilder)
  categoryUpdateForm: FormGroup = this.formBuilder.group({
     name: ['', [Validators.required]]
  })

  category: Category | undefined

  ngOnInit() {
    this.categoryService.getCategoryById(this.id()).subscribe({
      next: (result) => {
        this.category = result
        this.categoryUpdateForm.get('name')?.setValue(result.name)
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)
        this.isError = true
      }
    })
  }

  onFormSubmit(){
    this.isFormLoading = true
    this.isSuccess = false
    this.isError = false

    // create payload
    const payload: Category = {...this.category!, name: this.categoryUpdateForm.get('name')?.value}

    // execute update
    this.categoryService.updateCategory(payload).subscribe({
      next: result => {
        this.isSuccess = true
        this.category = result
        this.isFormLoading = false
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)

        this.isError = true
        this.isFormLoading = false
      }
    })
  }

  onCancel() {
    this.router.navigateByUrl('/categories')
  }

}
