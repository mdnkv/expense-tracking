import {Component, EventEmitter, inject, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";

import {OperationRequest, OperationTypes} from "../../models/operations.models";
import {Category} from "../../../categories/models/categories.models";
import {Account} from "../../../accounts/models/accounts.models";
import {CategoryService} from "../../../categories/services/category.service";
import {AccountService} from "../../../accounts/services/account.service";

@Component({
  selector: 'app-add-operation',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './add-operation.component.html',
  styleUrl: './add-operation.component.css'
})
export class AddOperationComponent implements OnInit{

  isShowModal: boolean = false
  categoriesList: Category[] = []
  accountsList: Account[] = []
  operationTypes = OperationTypes

  formBuilder: FormBuilder = inject(FormBuilder)
  categoryService: CategoryService = inject(CategoryService)
  accountService: AccountService = inject(AccountService)

  operationCreateForm: FormGroup = this.formBuilder.group({
    accountId: [null, [Validators.required]],
    categoryId: [null],
    amount: [0, [Validators.required, Validators.min(0)]],
    description: ['', [Validators.required]],
    operationDate: [null, [Validators.required]],
    type: ['EXPENSE', [Validators.required]]
  })

  @Output() onCreateOperation = new EventEmitter<OperationRequest>()

  ngOnInit() {
    // get user id
    const userIdString = localStorage.getItem("userId") as string
    const userId = Number.parseInt(userIdString)

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

    this.onCreateOperation.emit(payload)
    this.isShowModal = false
    this.operationCreateForm.reset()
  }

  onCloseFormModal(){}

}
