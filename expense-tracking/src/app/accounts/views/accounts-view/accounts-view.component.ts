import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {RouterLink} from "@angular/router";
import {Account, AccountTypes} from "../../models/accounts.models";
import {AccountService} from "../../services/account.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-accounts-view',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './accounts-view.component.html',
  styleUrl: './accounts-view.component.css'
})
export class AccountsViewComponent implements OnInit{

  isFormLoading: boolean = false
  isShowModal: boolean = false
  isError: boolean = false

  accountTypes  = AccountTypes
  accountsList: Account[] = []

  formBuilder: FormBuilder = inject(FormBuilder)
  accountCreateForm: FormGroup = this.formBuilder.group({
    name: ['', [Validators.required]],
    type: ['CASH', [Validators.required]]
  })

  accountService: AccountService = inject(AccountService)

  ngOnInit() {
    // get current user id
    const userId = localStorage.getItem("userId") as string
    const id = Number.parseInt(userId)

    // retrieve accounts for user
    this.accountService.getAllAccountsForUser(id).subscribe({
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

    // get user id
    const userId = localStorage.getItem("userId") as string
    const id = Number.parseInt(userId)

    // create payload
    const payload: Account = {
      userId: id,
      name: this.accountCreateForm.get('name')?.value,
      type: this.accountCreateForm.get('type')?.value
    }

    // execute request
    this.accountService.createAccount(payload).subscribe({
      next: result => {
        this.accountsList.push(result)

        this.accountCreateForm.reset()

        this.isFormLoading = false
        this.isShowModal = false
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)
        this.isFormLoading = false
        this.isError = true
      }
    })
  }

  onCloseFormModal(){
    // reset form
    this.accountCreateForm.reset()
    // close modal
    this.isShowModal = false
  }

  deleteAccount(id: number) {
    this.accountService.deleteAccount(id).subscribe({
      next: result => {
        this.accountsList = this.accountsList.filter(e => e.id != id)
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)
      }
    })
  }

}
