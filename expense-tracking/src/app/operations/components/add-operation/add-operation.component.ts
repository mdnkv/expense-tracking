import {Component, EventEmitter, inject, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";

import {Operation} from "../../models/operations.models";
import {Category} from "../../../categories/models/categories.models";
import {Account} from "../../../accounts/models/accounts.models";
import {CategoryService} from "../../../categories/services/category.service";
import {AccountService} from "../../../accounts/services/account.service";

import Swal from "sweetalert2";

@Component({
    selector: 'app-add-operation',
    imports: [ReactiveFormsModule],
    templateUrl: './add-operation.component.html',
    styleUrl: './add-operation.component.css'
})
export class AddOperationComponent implements OnInit{

  isShowModal: boolean = false
  categoriesList: Category[] = []
  accountsList: Account[] = []
  operationType: string = 'INCOME'

  formBuilder: FormBuilder = inject(FormBuilder)
  categoryService: CategoryService = inject(CategoryService)
  accountService: AccountService = inject(AccountService)

  operationCreateForm: FormGroup = this.formBuilder.group({
    accountId: [null, [Validators.required]],
    categoryId: [null],
    amount: [0, [Validators.required, Validators.min(0)]],
    description: ['', [Validators.required]],
    operationDate: [null, [Validators.required]]
  })

  @Output() onCreateOperation = new EventEmitter<Operation>()

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
    const payload: Operation = {
      userId: userId,
      accountId: this.operationCreateForm.get('accountId')?.value,
      amount: this.operationCreateForm.get('amount')?.value,
      currency: 'EUR',
      description: this.operationCreateForm.get('description')?.value,
      operationType: this.operationType,
      date: this.operationCreateForm.get('operationDate')?.value
    }

    if (this.operationCreateForm.get('categoryId')?.value != null){
      payload.categoryId = this.operationCreateForm.get('categoryId')?.value
    }

    this.onCreateOperation.emit(payload)
    this.isShowModal = false
    this.operationCreateForm.reset()
  }

  onCloseFormModal(){
    if (this.operationCreateForm.touched){
      // ask the user, if the user wants to close the modal and lost all data
      Swal.fire({
        title: 'Close the window',
        text: 'Do you want to close the window and lost all entered data?',
        icon: 'info',
        showConfirmButton: true,
        showCancelButton: true,
        confirmButtonText: 'Yes, close',
        cancelButtonText: 'No, keep it'
      }).then(result => {
        if (result.isConfirmed){
          this.operationCreateForm.reset()
          this.isShowModal = false
        }
      })
    } else {
      // close modal
      this.isShowModal = false
    }
  }

}
