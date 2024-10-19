import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {OperationRequest, OperationResponse, OperationTypes} from "../../models/operations.models";
import {Category} from "../../../categories/models/categories.models";
import {Account} from "../../../accounts/models/accounts.models";
import {CategoryService} from "../../../categories/services/category.service";
import {AccountService} from "../../../accounts/services/account.service";
import {OperationService} from "../../services/operation.service";
import {HttpErrorResponse} from "@angular/common/http";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-operations-view',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './operations-view.component.html',
  styleUrl: './operations-view.component.css'
})
export class OperationsViewComponent implements OnInit{

  isFormLoading: boolean = false
  isShowModal: boolean = false
  isError: boolean = false

  operationsList: OperationResponse[] = []
  categoriesList: Category[] = []
  accountsList: Account[] = []

  operationTypes = OperationTypes

  formBuilder: FormBuilder = inject(FormBuilder)

  categoryService: CategoryService = inject(CategoryService)
  accountService: AccountService = inject(AccountService)
  operationService: OperationService = inject(OperationService)

  operationCreateForm: FormGroup = this.formBuilder.group({
    accountId: [null, [Validators.required]],
    categoryId: [null],
    amount: [0, [Validators.required, Validators.min(0)]],
    description: ['', [Validators.required]],
    operationDate: [null, [Validators.required]],
    type: ['EXPENSE', [Validators.required]]
  })

  ngOnInit() {
    // get user id
    const userIdString = localStorage.getItem("userId") as string
    const userId = Number.parseInt(userIdString)

    // get operations for user
    this.operationService.getAllOperationsForUser(userId).subscribe({
      next: result => {
        this.operationsList = result
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

    // get accounts for user
    this.accountService.getAllAccountsForUser(userId).subscribe({
      next: result => {
        this.accountsList = result
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)
      }
    })
  }

  onFormSubmit(){
    this.isFormLoading = true
    this.isError = false

    // obtain the user id from local storage
    const userIdString = localStorage.getItem("userId") as string
    const userId = Number.parseInt(userIdString)

    // create payload
    const payload: OperationRequest = {
      userId: userId,
      accountId: this.operationCreateForm.get('accountId')?.value,
      amount: this.operationCreateForm.get('amount')?.value,
      currency: 'EUR',
      description: this.operationCreateForm.get('description')?.value,
      type: this.operationCreateForm.get('type')?.value,
      operationDate: this.operationCreateForm.get('operationDate')?.value
    }

    if (this.operationCreateForm.get('categoryId')?.value != null){
      payload.categoryId = this.operationCreateForm.get('categoryId')?.value
    }

    // execute request
    this.operationService.createOperation(payload).subscribe({
      next: result => {
        // add operation to operationsList
        this.operationsList.push(result)

        this.operationCreateForm.reset()
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
    this.operationCreateForm.reset()
    this.isShowModal = false
  }

  deleteOperation(id: number){
    this.operationService.deleteOperation(id).subscribe({
      next: result => {
        this.operationsList = this.operationsList.filter(e => e.id != id)
      },
      error: (err: HttpErrorResponse) =>{
        console.log(err)
      }
    })
  }

}
