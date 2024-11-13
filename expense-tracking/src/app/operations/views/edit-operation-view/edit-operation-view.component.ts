import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";

import {CategoryService} from "../../../categories/services/category.service";
import {AccountService} from "../../../accounts/services/account.service";
import {OperationService} from "../../services/operation.service";
import {Account} from "../../../accounts/models/accounts.models";
import {Category} from "../../../categories/models/categories.models";
import {OperationRequest, OperationResponse} from "../../models/operations.models";


@Component({
  selector: 'app-edit-operation-view',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './edit-operation-view.component.html',
  styleUrl: './edit-operation-view.component.css'
})
export class EditOperationViewComponent implements OnInit{

  isSuccess: boolean = false
  isError: boolean = false
  isFormLoading: boolean = false
  operationType: string = 'INCOME'

  activatedRoute: ActivatedRoute = inject(ActivatedRoute)
  router: Router = inject(Router)
  formBuilder: FormBuilder = inject(FormBuilder)

  categoryService: CategoryService = inject(CategoryService)
  accountService: AccountService = inject(AccountService)
  operationService: OperationService = inject(OperationService)

  accountsList: Account[] = []
  categoriesList: Category[] = []

  operation: OperationResponse | undefined

  operationUpdateForm: FormGroup = this.formBuilder.group({
    accountId: [null, [Validators.required]],
    categoryId: [null],
    amount: [0, [Validators.required, Validators.min(0)]],
    description: ['', [Validators.required]],
    operationDate: [null, [Validators.required]],
  })

  onFormSubmit(){
    this.isFormLoading = true
    this.isSuccess = false
    this.isError = false

    // get user id
    const userIdString = localStorage.getItem("userId") as string
    const userId = Number.parseInt(userIdString)

    // create payload
    const payload: OperationRequest = {
      id: this.operation!.id!,
      accountId: this.operationUpdateForm.get('accountId')?.value,
      amount: this.operationUpdateForm.get('amount')?.value,
      description: this.operationUpdateForm.get('description')?.value,
      operationDate: this.operationUpdateForm.get('operationDate')?.value,
      type: this.operationType,
      currency: this.operation!.currency,
      userId
    }

    // set category id
    if (this.operationUpdateForm.get('categoryId')?.value != null){
      payload.categoryId = this.operationUpdateForm.get('categoryId')?.value
    }

    // execute request
    this.operationService.updateOperation(payload).subscribe({
      next: result => {
        // update the operation instance
        this.operation = result

        // display success message
        this.isSuccess = true
        this.isFormLoading = false
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)

        // display error message
        this.isError = true
        this.isFormLoading = false
      }
    })
  }

  onCancel() {
    this.router.navigateByUrl('/operations')
  }

  ngOnInit() {
    // get current operation id
    const pathParam = this.activatedRoute.snapshot.paramMap.get('id')
    if (pathParam != null){
      // get operation id
      const operationId = Number.parseInt(pathParam)
      // get operation from server
      this.operationService.getOperationById(operationId).subscribe({
        next: result => {
          this.operation = result
          console.log(result)

          // update simple form fields
          this.operationUpdateForm.get('description')?.setValue(result.description)
          this.operationUpdateForm.get('amount')?.setValue(result.amount)
          this.operationUpdateForm.get('operationDate')?.setValue(result.operationDate)
          this.operationType = result.type
          // set fields with relationships
          if (result.account != null){
            this.operationUpdateForm.get('accountId')?.setValue(result.account.id)
          }
          if (result.category != null) {
            this.operationUpdateForm.get('categoryId')?.setValue(result.category.id)
          }

        },
        error: (err: HttpErrorResponse) => {
          console.log(err)
        }
      })

      // get user id
      const userIdString = localStorage.getItem("userId") as string
      const userId = Number.parseInt(userIdString)

      // get accounts for user
      this.accountService.getAllAccountsForUser(userId).subscribe({
        next: result => {
          this.accountsList = result
        },
        error: (err: HttpErrorResponse) => {
          console.log(err)
        }
      })

      // get categories for user
      this.categoryService.getAllCategoriesForUser(userId).subscribe({
        next: result =>{
          this.categoriesList = result
        },
        error: (err: HttpErrorResponse) => {
          console.log(err)
        }
      })

    } else {
      this.isError = true
    }
  }
}
