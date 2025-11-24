import {Component, inject, input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {AccountService} from "../../services/account.service";
import {Account} from "../../models/accounts.models";

@Component({
    selector: 'app-edit-account-view',
    imports: [ReactiveFormsModule],
    templateUrl: './edit-account-view.component.html',
    styleUrl: './edit-account-view.component.css'
})
export class EditAccountViewComponent implements OnInit{

  id = input.required<string>()

  router: Router = inject(Router)
  formBuilder: FormBuilder = inject(FormBuilder)

  isFormLoading: boolean = false
  isSuccess: boolean = false
  isError: boolean = false
  account: Account | undefined

  accountType: string = 'CASH'

  accountUpdateForm: FormGroup = this.formBuilder.group({
    name: ['', [Validators.required]],
  })
  accountService: AccountService = inject(AccountService)

  onCancel() {
    this.router.navigateByUrl('/accounts')
  }

  onFormSubmit(){
    this.isFormLoading = true
    this.isSuccess = false
    this.isError = false

    // create payload
    const payload: Account = {
      ...this.account!,
      name: this.accountUpdateForm.get('name')?.value,
      type: this.accountType
    }

    // execute the service method
    this.accountService.updateAccount(payload).subscribe({
      next: result =>{
        this.account = result
        this.isSuccess = true
        this.isFormLoading = false
      },
      error: (err: HttpErrorResponse) =>{
        console.log(err)
        this.isError = true
        this.isFormLoading = false
      }
    })
  }

  ngOnInit() {
    this.accountService.getAccountById(this.id()).subscribe({
      next: (result) => {
        this.account = result
        this.accountType = result.type
        this.accountUpdateForm.get('name')?.setValue(result.name)
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)
        this.isError = true
      }
    })
    // const pathParam = this.activatedRoute.snapshot.paramMap.get('id')
    // if (pathParam != null){
    //   const id = Number.parseInt(pathParam)
    //   this.accountService.getAccountById(id).subscribe({
    //     next: (result) => {
    //       this.account = result
    //       this.accountType = result.type
    //       this.accountUpdateForm.get('name')?.setValue(result.name)
    //     },
    //     error: (err: HttpErrorResponse) => {
    //       console.log(err)
    //     }
    //   })
    // } else {
    //   this.isError = true
    // }
  }

}
