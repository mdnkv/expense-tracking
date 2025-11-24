import {Component, EventEmitter, inject, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";

import {Operation} from "../../models/operations.models";
import {Category} from "../../../categories/models/categories.models";
import {Account} from "../../../accounts/models/accounts.models";
import {CategoryService} from "../../../categories/services/category.service";
import {AccountService} from "../../../accounts/services/account.service";

import Swal from "sweetalert2";
import {Currency} from "../../../currencies/models/currencies.models";
import {CurrencyService} from "../../../currencies/services/currency.service";

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
  currenciesList: Currency[] = []
  operationType: string = 'INCOME'

  formBuilder: FormBuilder = inject(FormBuilder)
  categoryService: CategoryService = inject(CategoryService)
  currencyService: CurrencyService = inject(CurrencyService)
  accountService: AccountService = inject(AccountService)

  operationCreateForm: FormGroup = this.formBuilder.group({
    accountId: [null, [Validators.required]],
    categoryId: [null],
    currencyId: [null, [Validators.required]],
    amount: [0, [Validators.required, Validators.min(0)]],
    description: ['', [Validators.required]],
    operationDate: [null, [Validators.required]]
  })

  @Output() onCreateOperation = new EventEmitter<Operation>()

  ngOnInit() {
    // get user id
    const userId = localStorage.getItem("UserId") as string

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
        if (this.accountsList.length > 0){
          const account = this.accountsList[0]
          this.operationCreateForm.get('accountId')?.setValue(account.id!)
        }
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)
      }
    })

    // get currencies for user
    this.currencyService.getAllCurrenciesForUser(userId).subscribe({
      next: result => {
        this.currenciesList = result
        if (this.currenciesList.length > 0){
          const currency = this.currenciesList[0]
          this.operationCreateForm.get('currencyId')?.setValue(currency.id!)
        }
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)
      }
    })
  }

  onFormSubmit(){
    // obtain the user id from local storage
    const userId = localStorage.getItem("UserId") as string

    // create payload
    const payload: Operation = {
      userId: userId,
      accountId: this.operationCreateForm.get('accountId')?.value,
      amount: this.operationCreateForm.get('amount')?.value,
      currencyId: this.operationCreateForm.get('currencyId')?.value,
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
